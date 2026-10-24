package DSA.BinarySearch;

public class SearchinRoatedSortedArray {

    public static int findTarget(int a[], int target){
        int start = 0;
        int end = a.length -1;

        while(start<=end){
            int mid = (start + end)/2;
            if(a[mid]==target){
                return mid;
            }
            if(a[start] <= a[mid]){
                //left is sorted apply BS
                if(a[start] <= target && target <= a[mid]){   //mera target start se badda hai aur mid se chota hai toh end minus hoga => target chota mid se matlab end update krna hoga
                    end = mid -1;
                }
                else{
                    start = mid +1;
                }
            }
            else{
                //right is sorted apply BS
                if(a[mid] <= target && target <= a[end]){  //abhi right side ke liye => mera target mid se badd hai aur end se chota => target badd mid se matlab start update krna hoga
                    start = mid +1;
                }
                else{
                    end = mid-1;
                }
            }
        }
        return -1;
        
    }
    

    public static void main(String[] args) {
        int a[] = {5,6,7,8,0,1,2};
        int target = 7;
        System.out.println(findTarget(a, target));
    }
    // 1. mid
    // 2. find which side is sorted (left or right) compare start element with mid
    // 3. apply BS in sorted side
}
