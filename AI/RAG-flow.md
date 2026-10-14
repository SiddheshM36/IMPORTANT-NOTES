Bhai, aaj apan ek proper Spring Boot + Spring AI + RAG project samajhte hain — zero se production-style flow tak. Maan le company ke paas 1,000 lines ka HR policy document hai aur employee chatbot se poochta hai:

> "Agar employee ne 3 saal complete kar liye hain, toh usko kitni annual leave milti hai?"

Hum dekhenge ki document kaise read hota hai, chunks kaise bante hain, embeddings kaise generate hoti hain, vector DB mein kya store hota hai, aur LLM final answer kaise deta hai.

# 1. Pehle complete architecture dekh

\#chatgpt-mermaid-\_r_th\_{font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";font-size:16px;fill:rgb(237, 237, 237);}@keyframes edge-animation-frame{from{stroke-dashoffset:0;}}@keyframes dash{to{stroke-dashoffset:0;}}#chatgpt-mermaid-\_r_th\_ .edge-animation-slow{stroke-dasharray:9,5!important;stroke-dashoffset:900;animation:dash 50s linear infinite;stroke-linecap:round;}#chatgpt-mermaid-\_r_th\_ .edge-animation-fast{stroke-dasharray:9,5!important;stroke-dashoffset:900;animation:dash 20s linear infinite;stroke-linecap:round;}#chatgpt-mermaid-\_r_th\_ .error-icon{fill:rgb(48, 48, 48);}#chatgpt-mermaid-\_r_th\_ .error-text{fill:rgb(237, 237, 237);stroke:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_th\_ .edge-thickness-normal{stroke-width:1px;}#chatgpt-mermaid-\_r_th\_ .edge-thickness-thick{stroke-width:3.5px;}#chatgpt-mermaid-\_r_th\_ .edge-pattern-solid{stroke-dasharray:0;}#chatgpt-mermaid-\_r_th\_ .edge-thickness-invisible{stroke-width:0;fill:none;}#chatgpt-mermaid-\_r_th\_ .edge-pattern-dashed{stroke-dasharray:3;}#chatgpt-mermaid-\_r_th\_ .edge-pattern-dotted{stroke-dasharray:2;}#chatgpt-mermaid-\_r_th\_ .marker{fill:rgb(175, 175, 175);stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_th\_ .marker.cross{stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_th\_ svg{font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";font-size:16px;}#chatgpt-mermaid-\_r_th\_ p{margin:0;}#chatgpt-mermaid-\_r_th\_ .label{font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";color:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_th\_ .cluster-label text{fill:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_th\_ .cluster-label span{color:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_th\_ .cluster-label span p{background-color:transparent;}#chatgpt-mermaid-\_r_th\_ .label text,#chatgpt-mermaid-\_r_th\_ span{fill:rgb(237, 237, 237);color:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_th\_ .node rect,#chatgpt-mermaid-\_r_th\_ .node circle,#chatgpt-mermaid-\_r_th\_ .node ellipse,#chatgpt-mermaid-\_r_th\_ .node polygon,#chatgpt-mermaid-\_r_th\_ .node path{fill:rgb(9, 23, 44);stroke:rgb(31, 78, 148);stroke-width:1px;}#chatgpt-mermaid-\_r_th\_ .rough-node .label text,#chatgpt-mermaid-\_r_th\_ .node .label text,#chatgpt-mermaid-\_r_th\_ .image-shape .label,#chatgpt-mermaid-\_r_th\_ .icon-shape .label{text-anchor:middle;}#chatgpt-mermaid-\_r_th\_ .node .katex path{fill:#000;stroke:#000;stroke-width:1px;}#chatgpt-mermaid-\_r_th\_ .rough-node .label,#chatgpt-mermaid-\_r_th\_ .node .label,#chatgpt-mermaid-\_r_th\_ .image-shape .label,#chatgpt-mermaid-\_r_th\_ .icon-shape .label{text-align:center;}#chatgpt-mermaid-\_r_th\_ .node.clickable{cursor:pointer;}#chatgpt-mermaid-\_r_th\_ .root .anchor path{fill:rgb(175, 175, 175)!important;stroke-width:0;stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_th\_ .arrowheadPath{fill:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_th\_ .edgePath .path{stroke:rgb(175, 175, 175);stroke-width:1px;}#chatgpt-mermaid-\_r_th\_ .flowchart-link{stroke:rgb(175, 175, 175);fill:none;}#chatgpt-mermaid-\_r_th\_ .edgeLabel{background-color:rgb(0, 0, 0);text-align:center;}#chatgpt-mermaid-\_r_th\_ .edgeLabel p{background-color:rgb(0, 0, 0);}#chatgpt-mermaid-\_r_th\_ .edgeLabel rect{opacity:0.5;background-color:rgb(0, 0, 0);fill:rgb(0, 0, 0);}#chatgpt-mermaid-\_r_th\_ .labelBkg{background-color:rgba(0, 0, 0, 0.5);}#chatgpt-mermaid-\_r_th\_ .cluster rect{fill:rgb(48, 48, 48);stroke:rgba(255, 255, 255, 0.15);stroke-width:1px;}#chatgpt-mermaid-\_r_th\_ .cluster text{fill:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_th\_ .cluster span{color:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_th\_ div.mermaidTooltip{position:absolute;text-align:center;max-width:200px;padding:2px;font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";font-size:12px;background:rgb(48, 48, 48);border:1px solid rgba(255, 255, 255, 0.15);border-radius:2px;pointer-events:none;z-index:100;}#chatgpt-mermaid-\_r_th\_ .flowchartTitleText{text-anchor:middle;font-size:18px;fill:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_th\_ rect.text{fill:none;stroke-width:0;}#chatgpt-mermaid-\_r_th\_ .icon-shape,#chatgpt-mermaid-\_r_th\_ .image-shape{background-color:rgb(0, 0, 0);text-align:center;}#chatgpt-mermaid-\_r_th\_ .icon-shape p,#chatgpt-mermaid-\_r_th\_ .image-shape p{background-color:rgb(0, 0, 0);padding:2px;}#chatgpt-mermaid-\_r_th\_ .icon-shape .label rect,#chatgpt-mermaid-\_r_th\_ .image-shape .label rect{opacity:0.5;background-color:rgb(0, 0, 0);fill:rgb(0, 0, 0);}#chatgpt-mermaid-\_r_th\_ .label-icon{display:inline-block;height:1em;overflow:visible;vertical-align:-0.125em;}#chatgpt-mermaid-\_r_th\_ .node .label-icon path{fill:currentColor;stroke:revert;stroke-width:revert;}#chatgpt-mermaid-\_r_th\_ .node .neo-node{stroke:rgb(31, 78, 148);}#chatgpt-mermaid-\_r_th\_ [data-look="neo"].node rect,#chatgpt-mermaid-\_r_th\_ [data-look="neo"].cluster rect,#chatgpt-mermaid-\_r_th\_ [data-look="neo"].node polygon{stroke:url(#chatgpt-mermaid-\_r_th\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_th\_ [data-look="neo"].swimlane.cluster rect{filter:none;}#chatgpt-mermaid-\_r_th\_ [data-look="neo"].node path{stroke:url(#chatgpt-mermaid-\_r_th\_-gradient);stroke-width:1px;}#chatgpt-mermaid-\_r_th\_ [data-look="neo"].node .outer-path{filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_th\_ [data-look="neo"].node .neo-line path{stroke:rgb(31, 78, 148);filter:none;}#chatgpt-mermaid-\_r_th\_ [data-look="neo"].node circle{stroke:url(#chatgpt-mermaid-\_r_th\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_th\_ [data-look="neo"].node circle .state-start{fill:#000000;}#chatgpt-mermaid-\_r_th\_ [data-look="neo"].icon-shape .icon{fill:url(#chatgpt-mermaid-\_r_th\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_th\_ [data-look="neo"].icon-shape .icon-neo path{stroke:url(#chatgpt-mermaid-\_r_th\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_th\_ .node text{font-size:14px;font-weight:600;letter-spacing:normal;fill:rgb(153, 206, 255);}#chatgpt-mermaid-\_r_th\_ .edgeLabels text{font-size:13px;font-weight:600;letter-spacing:-0.08px;fill:rgb(153, 206, 255);}#chatgpt-mermaid-\_r_th\_ .node tspan[font-weight="normal"],#chatgpt-mermaid-\_r_th\_ .edgeLabels tspan[font-weight="normal"]{font-weight:600;}#chatgpt-mermaid-\_r_th\_ .edgeLabel .label rect{opacity:1;rx:13px;ry:13px;fill:rgb(0, 14, 26);stroke:rgb(26, 62, 95);stroke-width:1px;}#chatgpt-mermaid-\_r_th\_ .node rect,#chatgpt-mermaid-\_r_th\_ .node circle,#chatgpt-mermaid-\_r_th\_ .node ellipse,#chatgpt-mermaid-\_r_th\_ .node polygon,#chatgpt-mermaid-\_r_th\_ .node path{fill:rgb(0, 40, 77);stroke:rgba(255, 255, 255, 0.1);stroke-width:1px;}#chatgpt-mermaid-\_r_th\_ .node rect{rx:16px;ry:16px;}#chatgpt-mermaid-\_r_th\_ .node.mermaid-decision .label-container{fill:rgb(0, 14, 26);stroke:rgb(26, 62, 95);stroke-dasharray:2,2;}#chatgpt-mermaid-\_r_th\_ .edgePaths .flowchart-link{stroke:rgb(175, 175, 175);stroke-width:1px;stroke-linecap:round;stroke-linejoin:round;}#chatgpt-mermaid-\_r_th\_ .marker{fill:rgb(175, 175, 175);stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_th\_ :root{--mermaid-font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";}HR Policy PDF1000 linesSpring Boot DocumentIngestionPDF ParserText extractionChunkingText ko smaller pieces meintodnaEmbedding ModelHar chunk → vectorVector DatabaseVectors + Text + MetadataEmployee QuestionSpring Boot RAG APISame Embedding ModelQuestion → vectorVector Similarity SearchTop-K Relevant ChunksPrompt + Question + ContextLLMAnswer + Source Reference

Bhai, ismein do alag flows hain:

- Ingestion flow: Documents ko ek baar process karke searchable banana.
- Query flow: Har user question par relevant chunks dhoondhna aur LLM se answer lena.

Har question par poora 1,000-line document LLM ko nahi bhejna padta.

# 2. Step 1 — Dummy 1,000-line document

Maan le `hr-leave-policy.txt` naam ki file hai.

Ismein alag-alag sections hain:

hr-leave-policy.txt

1,000 lines · Example document

Lines 1–150

Company overview, scope, definitions

Lines 151–350

Annual leave rules

Lines 351–500

Sick leave and emergency leave

Lines 501–700

Leave eligibility and employee tenure

Lines 701–850

Approval workflow

Lines 851–1000

Carry-forward rules and FAQs

Ye sirf samjhane ke liye example hai. Real PDF mein paragraphs, tables, headers aur page numbers bhi ho sakte hain.

Spring Boot application file ko parse karegi. Agar PDF hai toh PDF parser se text niklega; agar DOCX hai toh DOCX parser se.

Important: 1,000 lines ka matlab 1,000 chunks nahi hai. Chunking text length aur semantic boundaries ke basis par hoti hai, line count ke basis par zaroori nahi.

# 3. Step 2 — Document ko chunks mein kaise todte hain?

Chunking ka matlab ek bade document ko chhote text pieces mein divide karna.

Maan le hum example ke liye `400 tokens` ka chunk size aur `50 tokens` ka overlap rakhte hain. Ye initial configuration hai; actual values document aur retrieval testing ke hisaab se tune karni hoti hain.

Original document

Lines 1–1000 ka complete text

Chunking process

Chunk 1

Tokens 1–400

Company introduction + leave policy ki shuruaat

Chunk 2

Tokens 351–750

Chunk 1 ke last 50 tokens + agla text

Chunk 3

Tokens 701–1100

Chunk 2 ke last 50 tokens + agla text

Chunk 4 ... N

Aage ka document

Isi tarah baaki text process hota hai.

Ye token ranges samjhane ke liye hain, actual document se nikle hue numbers nahi.

### Overlap kyun rakhte hain?

Maan le ek rule ka first part Chunk 1 ke end mein hai aur uska important condition Chunk 2 ke beginning mein. Overlap se boundary par context lose hone ka chance kam hota hai.

Dhyan de: Spring AI ka `TokenTextSplitter` text ko token count ke hisaab se split karne mein help karta hai. Upar wala 50-token overlap conceptual example hai; ise implement karne ke liye overlap-supporting splitter ya custom chunking logic configure karna padega. Built-in splitter ko automatically 50-token overlap wala mat samajhna.&#x20;

[image](https://www.google.com/s2/favicons?domain=https://docs.spring.io\&sz=32)

Home



## 4. Step 3 — Har chunk ki embedding kaise banti hai?

Ab maan le Chunk 2 mein ye text hai:

> Employees who complete three years of service are eligible for 24 days of annual leave per calendar year.

Embedding model is text ko numerical vector mein convert karta hai.

Input text

Employees completing three years receive 24 days of annual leave.

Embedding model

Example: OpenAI `text-embedding-3-small`

Numerical vector (illustrative)

[0.021, -0.174, 0.083, 0.412, -0.056, ...]

Actual vector mein hundreds ya thousands of dimensions ho sakte hain, selected model/configuration ke according.

Embedding model text ka semantic representation banata hai. Similar meaning wale texts ke vectors generally vector space mein ek doosre ke paas hote hain.

Example:

- "Annual leave entitlement after three years"
- "Kitni chhutti milegi 3 years complete karne ke baad?"

Dono ki wording alag hai, lekin meaning similar hai. Semantic search isi similarity ka fayda uthati hai.

Important: har chunk ke liye embedding generate hoti hai. 100 chunks hue toh roughly 100 chunk embeddings; 500 chunks hue toh roughly 500, assuming har chunk ko separately embed kiya ja raha hai.

## 5. Step 4 — Vector DB mein kya store hota hai?

Maan le hum PostgreSQL + pgvector use kar rahe hain. Spring AI ka `VectorStore` interface embeddings ko store karne aur similar documents retrieve karne ke liye use hota hai.&#x20;

[image](https://www.google.com/s2/favicons?domain=https://docs.spring.io\&sz=32)

Home

+1



Ek simplified record kuch aisa ho sakta hai:

| Field              | Example value                                       |
| ------------------ | --------------------------------------------------- |
| `id`               | `chunk-002`                                         |
| `content`          | Employees completing three years receive 24 days... |
| `embedding`        | `[0.021, -0.174, ...]`                              |
| `source`           | `hr-leave-policy.pdf`                               |
| `page_number`      | `12`                                                |
| `department`       | `HR`                                                |
| `document_version` | `v3`                                                |

Actual Spring AI/pgvector schema aur column names implementation ke hisaab se different ho sakte hain.

Database mein sirf vector nahi hota. Original chunk text aur metadata bhi store karte hain, taaki search ke baad LLM ko readable text diya ja sake aur answer ka source pata chale.

100 chunks hue toh approximately 100 records; 500 chunks hue toh approximately 500 records.

Ek aur important point: vector DB apne aap embedding model nahi hai. Model vector banata hai; vector DB vector aur associated data store/search karta hai.

# 6. Spring Boot mein ingestion code

Ab actual implementation dekh. Maan le document `src/main/resources/hr-leave-policy.txt` mein hai.

Spring AI ke current ETL approach mein `DocumentReader` text load karta hai, `TokenTextSplitter` chunks banata hai, aur `VectorStore` unhe store karta hai.&#x20;

[image](https://www.google.com/s2/favicons?domain=https://docs.spring.io\&sz=32)

Home



Conceptual Java example:

```
@Service
public class DocumentIngestionService {

    private final VectorStore vectorStore;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingest(Resource resource) {

        // 1. Read the complete text file
        TextReader reader = new TextReader(resource);
        List<Document> documents = reader.read();

        // 2. Split into smaller chunks
        TokenTextSplitter splitter =
                TokenTextSplitter.builder()
                        .withChunkSize(400)
                        .build();

        List<Document> chunks = splitter.split(documents);

        // 3. Add source metadata to every chunk
        for (Document chunk : chunks) {
            chunk.getMetadata().put(
                    "documentType", "HR_POLICY");
        }

        // 4. VectorStore uses the configured
        // embedding model and stores the chunks
        vectorStore.add(chunks);
    }
}
```

Is example mein exact constructors aur APIs Spring AI version ke hisaab se adjust karne pad sakte hain. PDF ke liye `PagePdfDocumentReader` ya suitable PDF parser use karna hoga.

### Code ke peeche kya hota hai?

1. File read hoti hai aur text `Document` objects mein aata hai.
2. Splitter us text ko smaller `Document` chunks mein divide karta hai.
3. `vectorStore.add(chunks)` configured embedding model se vectors generate karwata hai.
4. VectorStore chunks, vectors aur metadata ko database mein save karta hai.

Yahan embedding model ko manually call karne ki zaroorat nahi, kyunki `VectorStore` integration embedding generation handle kar sakta hai.

# 7. Step 5 — User question aaya, ab retrieval kaise hoga?

Ab ingestion complete hai. Vector DB mein saare chunks stored hain.

Employee poochta hai:

> "I have completed 3 years. How many annual leave days can I take?"

Query flow:

Chat LLMVector DBEmbedding ModelSpring BootUserChat LLMVector DBEmbedding ModelSpring BootUser#chatgpt-mermaid-\_r_vc\_{font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";font-size:16px;fill:rgb(237, 237, 237);}@keyframes edge-animation-frame{from{stroke-dashoffset:0;}}@keyframes dash{to{stroke-dashoffset:0;}}#chatgpt-mermaid-\_r_vc\_ .edge-animation-slow{stroke-dasharray:9,5!important;stroke-dashoffset:900;animation:dash 50s linear infinite;stroke-linecap:round;}#chatgpt-mermaid-\_r_vc\_ .edge-animation-fast{stroke-dasharray:9,5!important;stroke-dashoffset:900;animation:dash 20s linear infinite;stroke-linecap:round;}#chatgpt-mermaid-\_r_vc\_ .error-icon{fill:rgb(48, 48, 48);}#chatgpt-mermaid-\_r_vc\_ .error-text{fill:rgb(237, 237, 237);stroke:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_vc\_ .edge-thickness-normal{stroke-width:1px;}#chatgpt-mermaid-\_r_vc\_ .edge-thickness-thick{stroke-width:3.5px;}#chatgpt-mermaid-\_r_vc\_ .edge-pattern-solid{stroke-dasharray:0;}#chatgpt-mermaid-\_r_vc\_ .edge-thickness-invisible{stroke-width:0;fill:none;}#chatgpt-mermaid-\_r_vc\_ .edge-pattern-dashed{stroke-dasharray:3;}#chatgpt-mermaid-\_r_vc\_ .edge-pattern-dotted{stroke-dasharray:2;}#chatgpt-mermaid-\_r_vc\_ .marker{fill:rgb(175, 175, 175);stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_vc\_ .marker.cross{stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_vc\_ svg{font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";font-size:16px;}#chatgpt-mermaid-\_r_vc\_ p{margin:0;}#chatgpt-mermaid-\_r_vc\_ .actor{stroke:rgb(31, 78, 148);fill:rgb(9, 23, 44);stroke-width:1;}#chatgpt-mermaid-\_r_vc\_ rect.actor.outer-path[data-look="neo"]{filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_vc\_ rect.note[data-look="neo"]{stroke:rgb(58, 132, 63);fill:rgb(48, 48, 48);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_vc\_ text.actor>tspan{fill:rgb(237, 237, 237);stroke:none;}#chatgpt-mermaid-\_r_vc\_ .actor-line{stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_vc\_ .innerArc{stroke-width:1.5;stroke-dasharray:none;}#chatgpt-mermaid-\_r_vc\_ .messageLine0{stroke-width:1.5;stroke-dasharray:none;stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_vc\_ .messageLine1{stroke-width:1.5;stroke-dasharray:2,2;stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_vc\_ [id$="-arrowhead"] path{fill:rgb(175, 175, 175);stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_vc\_ .sequenceNumber{fill:#505050;}#chatgpt-mermaid-\_r_vc\_ [id$="-sequencenumber"]{fill:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_vc\_ [id$="-crosshead"] path{fill:rgb(175, 175, 175);stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_vc\_ .messageText{fill:rgb(237, 237, 237);stroke:none;}#chatgpt-mermaid-\_r_vc\_ .labelBox{stroke:rgba(255, 255, 255, 0.15);fill:rgb(0, 0, 0);filter:none;}#chatgpt-mermaid-\_r_vc\_ .labelText,#chatgpt-mermaid-\_r_vc\_ .labelText>tspan{fill:rgb(237, 237, 237);stroke:none;}#chatgpt-mermaid-\_r_vc\_ .loopText,#chatgpt-mermaid-\_r_vc\_ .loopText>tspan{fill:rgb(237, 237, 237);stroke:none;}#chatgpt-mermaid-\_r_vc\_ .sectionTitle,#chatgpt-mermaid-\_r_vc\_ .sectionTitle>tspan{fill:rgb(237, 237, 237);stroke:none;}#chatgpt-mermaid-\_r_vc\_ .loopLine{stroke-width:2px;stroke-dasharray:2,2;stroke:rgba(255, 255, 255, 0.15);fill:rgba(255, 255, 255, 0.15);}#chatgpt-mermaid-\_r_vc\_ .note{stroke:rgb(58, 132, 63);fill:rgb(48, 48, 48);}#chatgpt-mermaid-\_r_vc\_ .noteText,#chatgpt-mermaid-\_r_vc\_ .noteText>tspan{fill:rgb(237, 237, 237);stroke:none;font-weight:normal;}#chatgpt-mermaid-\_r_vc\_ .activation0{fill:rgb(48, 48, 48);stroke:hsl(0, 0%, 8.8235294118%);}#chatgpt-mermaid-\_r_vc\_ .activation1{fill:rgb(48, 48, 48);stroke:hsl(0, 0%, 8.8235294118%);}#chatgpt-mermaid-\_r_vc\_ .activation2{fill:rgb(48, 48, 48);stroke:hsl(0, 0%, 8.8235294118%);}#chatgpt-mermaid-\_r_vc\_ .actorPopupMenu{position:absolute;}#chatgpt-mermaid-\_r_vc\_ .actorPopupMenuPanel{position:absolute;fill:rgb(9, 23, 44);box-shadow:0px 8px 16px 0px rgba(0,0,0,0.2);filter:drop-shadow(3px 5px 2px rgb(0 0 0 / 0.4));}#chatgpt-mermaid-\_r_vc\_ .actor-man circle,#chatgpt-mermaid-\_r_vc\_ line{fill:rgb(9, 23, 44);stroke-width:2px;}#chatgpt-mermaid-\_r_vc\_ g rect.rect{filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));stroke:rgb(31, 78, 148);}#chatgpt-mermaid-\_r_vc\_ .node .neo-node{stroke:rgb(31, 78, 148);}#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].node rect,#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].cluster rect,#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].node polygon{stroke:url(#chatgpt-mermaid-\_r_vc\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].swimlane.cluster rect{filter:none;}#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].node path{stroke:url(#chatgpt-mermaid-\_r_vc\_-gradient);stroke-width:1px;}#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].node .outer-path{filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].node .neo-line path{stroke:rgb(31, 78, 148);filter:none;}#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].node circle{stroke:url(#chatgpt-mermaid-\_r_vc\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].node circle .state-start{fill:#000000;}#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].icon-shape .icon{fill:url(#chatgpt-mermaid-\_r_vc\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_vc\_ [data-look="neo"].icon-shape .icon-neo path{stroke:url(#chatgpt-mermaid-\_r_vc\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_vc\_ :root{--mermaid-font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";}Annual leave after 3 years?Embed the questionQuery vectorSimilarity search (top 4)Relevant chunks + metadataQuestion + retrieved contextAnswer based on contextAnswer + source reference

### Query embedding

Question ka bhi vector banega — usi embedding model se jisse document chunks ke vectors banaye the.

Maan le question vector:

`[0.018, -0.162, 0.091, 0.398, ...]`

Vector DB is query vector ko stored vectors ke saath compare karega. Similarity ke liye cosine similarity ya configured distance metric use ho sakta hai.

### Top-K retrieval

Maan le `topK = 4` configure kiya hai. Database 4 relevant chunks return karne ki koshish karega.

| Rank | Retrieved chunk                        | Relevance |
| ---- | -------------------------------------- | --------- |
| 1    | Annual leave eligibility after 3 years | High      |
| 2    | Annual leave entitlement table         | High      |
| 3    | Leave calculation by tenure            | Medium    |
| 4    | Leave approval procedure               | Lower     |

Ye illustrative results hain, actual scores nahi. Production mein similarity threshold bhi configure kar sakte hain, taaki irrelevant chunks ko answer mein use na kiya jaye.

## 8. Step 6 — LLM ko kya bhejte hain?

Ab Spring Boot retrieved text ko user ke question ke saath prompt mein combine karta hai.

```
SYSTEM:
You are an HR policy assistant.
Answer only using the supplied policy context.
If the answer is not present, say that you could not find it.
Do not invent leave entitlements.

RETRIEVED CONTEXT:
[Chunk 1]
Employees completing three years of service are eligible
for 24 days of annual leave per calendar year.

[Chunk 2]
Annual leave must be requested through the HR portal
and is subject to the company's approval process.

USER QUESTION:
I have completed 3 years. How many annual leave days
can I take?
```

LLM response:

> According to the supplied policy, employees completing three years of service are eligible for 24 days of annual leave per calendar year. Leave is subject to the company's approval process.

Notice kar: LLM ko poora 1,000-line document nahi bheja. Sirf retrieved context aur question bheja.

Aur RAG ka matlab ye nahi ki hallucinations completely eliminate ho gayi. Isliye source references, retrieval evaluation aur answer validation important hain.

# 9. Spring AI mein retrieval + LLM ka code

Conceptually, basic implementation aisi dikhegi:

```
@Service
public class HrRagService {

    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public HrRagService(
            VectorStore vectorStore,
            ChatClient.Builder chatClientBuilder) {

        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public String ask(String question) {

        // 1. Retrieve relevant chunks
        List<Document> chunks =
                vectorStore.similaritySearch(
                        SearchRequest.builder()
                                .query(question)
                                .topK(4)
                                .build());

        // 2. Build context from retrieved chunks
        String context = chunks.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        // 3. Send question + context to the LLM
        return chatClient.prompt()
                .system("""
                    Answer using only the supplied context.
                    If the answer is missing, say so.
                    """)
                .user("""
                    Context:
                    %s

                    Question:
                    %s
                    """.formatted(context, question))
                .call()
                .content();
    }
}
```

Ye basic teaching example hai. Production mein empty retrieval, similarity threshold, source citations, metadata filters, token limits, errors, prompt-injection protection aur authorization bhi handle karne honge. Spring AI ke `VectorStore` se similarity search aur `ChatClient` se model call kiya ja sakta hai.&#x20;

[image](https://www.google.com/s2/favicons?domain=https://docs.spring.io\&sz=32)

Home

+1



# 10. Kaunsa embedding model aur kaunsa LLM use karein?

Bhai, ye dono alag models hain. Tere learning project ke liye ye starting setup rakh:

Embedding model

## OpenAI `text-embedding-3-small`

Document chunks aur user queries ko vectors mein convert karega.

Chat LLM

## An approved GPT mini-class model

Retrieved context samjhega aur natural-language answer generate karega. Exact model provider ki current availability aur organization ki approval ke according select karna.

Vector DB

## PostgreSQL + pgvector

Chunk text, embeddings aur source metadata store/search karega.

Personal project mein ye setup seekhne ke liye achha starting point hai. Client production mein approved embedding endpoint aur approved LLM endpoint use karna. Agar external APIs allowed nahi hain, toh internal model-serving option evaluate karna padega.

# 11. Documents update honge toh kya hoga?

Maan le HR ne policy ka version 3 publish kiya aur annual leave 24 se 26 days kar di.

Purane chunks database mein reh gaye toh chatbot galat answer de sakta hai. Isliye ingestion pipeline mein document versioning aur updates handle karna zaroori hai.

\#chatgpt-mermaid-\_r_113\_{font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";font-size:16px;fill:rgb(237, 237, 237);}@keyframes edge-animation-frame{from{stroke-dashoffset:0;}}@keyframes dash{to{stroke-dashoffset:0;}}#chatgpt-mermaid-\_r_113\_ .edge-animation-slow{stroke-dasharray:9,5!important;stroke-dashoffset:900;animation:dash 50s linear infinite;stroke-linecap:round;}#chatgpt-mermaid-\_r_113\_ .edge-animation-fast{stroke-dasharray:9,5!important;stroke-dashoffset:900;animation:dash 20s linear infinite;stroke-linecap:round;}#chatgpt-mermaid-\_r_113\_ .error-icon{fill:rgb(48, 48, 48);}#chatgpt-mermaid-\_r_113\_ .error-text{fill:rgb(237, 237, 237);stroke:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_113\_ .edge-thickness-normal{stroke-width:1px;}#chatgpt-mermaid-\_r_113\_ .edge-thickness-thick{stroke-width:3.5px;}#chatgpt-mermaid-\_r_113\_ .edge-pattern-solid{stroke-dasharray:0;}#chatgpt-mermaid-\_r_113\_ .edge-thickness-invisible{stroke-width:0;fill:none;}#chatgpt-mermaid-\_r_113\_ .edge-pattern-dashed{stroke-dasharray:3;}#chatgpt-mermaid-\_r_113\_ .edge-pattern-dotted{stroke-dasharray:2;}#chatgpt-mermaid-\_r_113\_ .marker{fill:rgb(175, 175, 175);stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_113\_ .marker.cross{stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_113\_ svg{font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";font-size:16px;}#chatgpt-mermaid-\_r_113\_ p{margin:0;}#chatgpt-mermaid-\_r_113\_ .label{font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";color:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_113\_ .cluster-label text{fill:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_113\_ .cluster-label span{color:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_113\_ .cluster-label span p{background-color:transparent;}#chatgpt-mermaid-\_r_113\_ .label text,#chatgpt-mermaid-\_r_113\_ span{fill:rgb(237, 237, 237);color:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_113\_ .node rect,#chatgpt-mermaid-\_r_113\_ .node circle,#chatgpt-mermaid-\_r_113\_ .node ellipse,#chatgpt-mermaid-\_r_113\_ .node polygon,#chatgpt-mermaid-\_r_113\_ .node path{fill:rgb(9, 23, 44);stroke:rgb(31, 78, 148);stroke-width:1px;}#chatgpt-mermaid-\_r_113\_ .rough-node .label text,#chatgpt-mermaid-\_r_113\_ .node .label text,#chatgpt-mermaid-\_r_113\_ .image-shape .label,#chatgpt-mermaid-\_r_113\_ .icon-shape .label{text-anchor:middle;}#chatgpt-mermaid-\_r_113\_ .node .katex path{fill:#000;stroke:#000;stroke-width:1px;}#chatgpt-mermaid-\_r_113\_ .rough-node .label,#chatgpt-mermaid-\_r_113\_ .node .label,#chatgpt-mermaid-\_r_113\_ .image-shape .label,#chatgpt-mermaid-\_r_113\_ .icon-shape .label{text-align:center;}#chatgpt-mermaid-\_r_113\_ .node.clickable{cursor:pointer;}#chatgpt-mermaid-\_r_113\_ .root .anchor path{fill:rgb(175, 175, 175)!important;stroke-width:0;stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_113\_ .arrowheadPath{fill:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_113\_ .edgePath .path{stroke:rgb(175, 175, 175);stroke-width:1px;}#chatgpt-mermaid-\_r_113\_ .flowchart-link{stroke:rgb(175, 175, 175);fill:none;}#chatgpt-mermaid-\_r_113\_ .edgeLabel{background-color:rgb(0, 0, 0);text-align:center;}#chatgpt-mermaid-\_r_113\_ .edgeLabel p{background-color:rgb(0, 0, 0);}#chatgpt-mermaid-\_r_113\_ .edgeLabel rect{opacity:0.5;background-color:rgb(0, 0, 0);fill:rgb(0, 0, 0);}#chatgpt-mermaid-\_r_113\_ .labelBkg{background-color:rgba(0, 0, 0, 0.5);}#chatgpt-mermaid-\_r_113\_ .cluster rect{fill:rgb(48, 48, 48);stroke:rgba(255, 255, 255, 0.15);stroke-width:1px;}#chatgpt-mermaid-\_r_113\_ .cluster text{fill:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_113\_ .cluster span{color:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_113\_ div.mermaidTooltip{position:absolute;text-align:center;max-width:200px;padding:2px;font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";font-size:12px;background:rgb(48, 48, 48);border:1px solid rgba(255, 255, 255, 0.15);border-radius:2px;pointer-events:none;z-index:100;}#chatgpt-mermaid-\_r_113\_ .flowchartTitleText{text-anchor:middle;font-size:18px;fill:rgb(237, 237, 237);}#chatgpt-mermaid-\_r_113\_ rect.text{fill:none;stroke-width:0;}#chatgpt-mermaid-\_r_113\_ .icon-shape,#chatgpt-mermaid-\_r_113\_ .image-shape{background-color:rgb(0, 0, 0);text-align:center;}#chatgpt-mermaid-\_r_113\_ .icon-shape p,#chatgpt-mermaid-\_r_113\_ .image-shape p{background-color:rgb(0, 0, 0);padding:2px;}#chatgpt-mermaid-\_r_113\_ .icon-shape .label rect,#chatgpt-mermaid-\_r_113\_ .image-shape .label rect{opacity:0.5;background-color:rgb(0, 0, 0);fill:rgb(0, 0, 0);}#chatgpt-mermaid-\_r_113\_ .label-icon{display:inline-block;height:1em;overflow:visible;vertical-align:-0.125em;}#chatgpt-mermaid-\_r_113\_ .node .label-icon path{fill:currentColor;stroke:revert;stroke-width:revert;}#chatgpt-mermaid-\_r_113\_ .node .neo-node{stroke:rgb(31, 78, 148);}#chatgpt-mermaid-\_r_113\_ [data-look="neo"].node rect,#chatgpt-mermaid-\_r_113\_ [data-look="neo"].cluster rect,#chatgpt-mermaid-\_r_113\_ [data-look="neo"].node polygon{stroke:url(#chatgpt-mermaid-\_r_113\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_113\_ [data-look="neo"].swimlane.cluster rect{filter:none;}#chatgpt-mermaid-\_r_113\_ [data-look="neo"].node path{stroke:url(#chatgpt-mermaid-\_r_113\_-gradient);stroke-width:1px;}#chatgpt-mermaid-\_r_113\_ [data-look="neo"].node .outer-path{filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_113\_ [data-look="neo"].node .neo-line path{stroke:rgb(31, 78, 148);filter:none;}#chatgpt-mermaid-\_r_113\_ [data-look="neo"].node circle{stroke:url(#chatgpt-mermaid-\_r_113\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_113\_ [data-look="neo"].node circle .state-start{fill:#000000;}#chatgpt-mermaid-\_r_113\_ [data-look="neo"].icon-shape .icon{fill:url(#chatgpt-mermaid-\_r_113\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_113\_ [data-look="neo"].icon-shape .icon-neo path{stroke:url(#chatgpt-mermaid-\_r_113\_-gradient);filter:drop-shadow( 1px 2px 2px rgba(185,185,185,1));}#chatgpt-mermaid-\_r_113\_ .node text{font-size:14px;font-weight:600;letter-spacing:normal;fill:rgb(153, 206, 255);}#chatgpt-mermaid-\_r_113\_ .edgeLabels text{font-size:13px;font-weight:600;letter-spacing:-0.08px;fill:rgb(153, 206, 255);}#chatgpt-mermaid-\_r_113\_ .node tspan[font-weight="normal"],#chatgpt-mermaid-\_r_113\_ .edgeLabels tspan[font-weight="normal"]{font-weight:600;}#chatgpt-mermaid-\_r_113\_ .edgeLabel .label rect{opacity:1;rx:13px;ry:13px;fill:rgb(0, 14, 26);stroke:rgb(26, 62, 95);stroke-width:1px;}#chatgpt-mermaid-\_r_113\_ .node rect,#chatgpt-mermaid-\_r_113\_ .node circle,#chatgpt-mermaid-\_r_113\_ .node ellipse,#chatgpt-mermaid-\_r_113\_ .node polygon,#chatgpt-mermaid-\_r_113\_ .node path{fill:rgb(0, 40, 77);stroke:rgba(255, 255, 255, 0.1);stroke-width:1px;}#chatgpt-mermaid-\_r_113\_ .node rect{rx:16px;ry:16px;}#chatgpt-mermaid-\_r_113\_ .node.mermaid-decision .label-container{fill:rgb(0, 14, 26);stroke:rgb(26, 62, 95);stroke-dasharray:2,2;}#chatgpt-mermaid-\_r_113\_ .edgePaths .flowchart-link{stroke:rgb(175, 175, 175);stroke-width:1px;stroke-linecap:round;stroke-linejoin:round;}#chatgpt-mermaid-\_r_113\_ .marker{fill:rgb(175, 175, 175);stroke:rgb(175, 175, 175);}#chatgpt-mermaid-\_r_113\_ :root{--mermaid-font-family:-apple-system-body,ui-sans-serif,-apple-system,system-ui,"Segoe UI",Helvetica,"Apple Color Emoji",Arial,sans-serif,"Segoe UI Emoji","Segoe UI Symbol";}New PDF uploadedParse and splitGenerate new embeddingsStore new versionValidate successful ingestionActivate new versionRetire old chunks

Production mein new version ko pehle successfully ingest aur validate karo, phir activate karo. Purane chunks ko delete karne ka decision document ID aur version ke basis par lo — poore vector DB ko clear mat karna.

Aur haan, large document sets ke liye embeddings ko batches mein process kar sakte ho, rate limits handle kar sakte ho aur failed batches retry kar sakte ho.

## 12. Ab poora flow ek line mein yaad rakh

PDF → text extraction → chunking → embedding model → vector DB → user question → query embedding → similarity search → relevant chunks → LLM → grounded answer.

Spring AI ka official [ETL documentation](https://docs.spring.io/spring-ai/reference/api/etl-pipeline.html) aur [Vector DB documentation](https://docs.spring.io/spring-ai/reference/api/vectordbs.html) isi ingestion aur retrieval foundation ko explain karte hain.

Agla practical step ye hoga ki isi example ko ek runnable Spring Boot project mein implement karein — Maven dependencies, `application.yml`, PostgreSQL/pgvector Docker setup, document upload API aur chat API ke saath.
