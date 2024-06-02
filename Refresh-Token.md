<h2>CREATE A METHOD TO GENERATE REFRESH TOKEN</h2>

```
//generate refreshToken 
    public String generateRefreshToken(String subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("Blog Application")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(3)))
                .signWith(SignatureAlgorithm.HS256, secret_key.getBytes())
                .compact();
    }
```

<h2>METHOD TO CRAETE A ACCESS TOKEN FROM REFRESH TOKEN</h2>

```
public UserResponse refreshToken(HttpServletRequest request, HttpServletResponse response){
        String getAuthHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(getAuthHeader == null || !getAuthHeader.startsWith("Bearer ")){
            return null;
        }
        else{
            String refreshToken = getAuthHeader.substring(7);
            String userEmail = getUserName(refreshToken);
            System.out.println(userEmail);
            if(userEmail!=null){
                UserModel getUser = userRepo.findByEmail(userEmail).orElseThrow();
                if(validateToken(refreshToken, userEmail)){
                    String newAccessToken = generateToken(userEmail);
                    UserResponse userResponse = new UserResponse();
                    userResponse.setAccessToken(newAccessToken);
                    userResponse.setRefreshToken(refreshToken);
                    System.out.println(userResponse.toString());
                    return userResponse;
                }
                else{
                    throw new RuntimeException("Token not valid");
                }
            }
            throw new RuntimeException("User not found");

        }
        
    }
```

<h2>CONTROLLER TO HANDLE LOGIN AND CREATE ACCESS TOKEN FROM REFRESH TOKEN</h2>

```
@PostMapping(path = "/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){
        System.out.println("API CALLED");
        String accessToken = jwtConfig.generateToken(request.email);
        String refreshToken = jwtConfig.generateRefreshToken(request.email);
        UserResponse userResponse = new UserResponse();
        userResponse.setAccessToken(accessToken);
        userResponse.setRefreshToken(refreshToken);
        System.out.println(accessToken);


        //return  new ResponseEntity<UserResponse>(HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
    }


    // FOR REFRESH TOKEN 
    @PostMapping("/refresh-token")
    public ResponseEntity<UserResponse> getAccessTokenFromRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtConfig.refreshToken(request, response));
    }
```
