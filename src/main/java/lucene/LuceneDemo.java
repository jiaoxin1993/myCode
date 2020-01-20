package lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by admin on 2019/12/16.
 */
public class LuceneDemo {
    //创建索引  (查看索引  F:\lucene-8.3.1\luke luke.bat)
    @Test
    public void testIndex() throws Exception{
        //1、 创建一个indexwriter对象
        Directory directory = FSDirectory.open(Paths.get("F:/temp/index"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory,config);
            //指定索引库的存放位置 Directory对象
            //指定一个分词器，对文档内容进行分析
        //2、创建document对象
        Document document = new Document();
        //3、创建field对象（域），将field添加到Document对象中
        File fileDir = new File("F:\\resource");
        File[] files = fileDir.listFiles();
        for (File file:files){
            //文件名称
            String file_name = file.getName();
            Field fileNameField = new TextField("fileName",file_name, Field.Store.YES);
            //文件大小
            String file_size = FileUtils.sizeOf(file)+"";
            System.out.println(file_size+"--------");
            Field fileSizeField = new StoredField("fileSize",file_size);
            //文件路径
            String file_path = file.getPath();
            System.out.println(file_path+"--------");
            Field filePathField = new StoredField("filePath",file_path);
            //文件内容
            String file_context = FileUtils.readFileToString(file);
            Field fileContextField = new TextField("fileContext",file_context, Field.Store.NO);
            document.add(fileNameField);
            document.add(fileSizeField);
            document.add(filePathField);
            document.add(fileContextField);
        //4、使用indexWriter对象将document对象写入索引库，此过程进行索引创建，并将索引和document对象写入索引库
            indexWriter.addDocument(document);
        }
        //5、关闭流
        indexWriter.close();
    }
    //查询所有(子类查询)
    @Test
    public void testALLSearch() throws Exception{
        IndexSearcher indexSearcher = getIndexSearcher();
        //4、创建一个查询Query
        Query query = new MatchAllDocsQuery();
        printResult(indexSearcher,query);
        //7、关闭流
        indexSearcher.getIndexReader().close();
    }
    //精度查询（子类查询）
    @Test
    public void testSearch() throws Exception{
        IndexSearcher indexSearcher = getIndexSearcher();
        //4、创建一个查询Query
        Query query = new TermQuery(new Term("fileContext","java"));
        printResult(indexSearcher,query);
        //7、关闭流
        indexSearcher.getIndexReader().close();
    }
    //解析查询（通过语法查询）
    @Test
    public void testQuery() throws Exception{
        IndexSearcher indexSearcher = getIndexSearcher();
        //参数1 默认查询的域（单个域）
        QueryParser queryParser = new QueryParser("fileContext",new IKAnalyzer());
        //查询所有 *:*
        Query query = queryParser.parse("重定向");
        printResult(indexSearcher, query);
        indexSearcher.getIndexReader().close();
    }
    //解析查询（通过语法查询）
    @Test
    public void testQuerys() throws Exception{
        IndexSearcher indexSearcher = getIndexSearcher();
        //参数1 默认查询的域（多域）
        String[] fields = {"fileName","fileContext"};
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields,new IKAnalyzer());
        //查询所有 *:*
        Query query = queryParser.parse("重定向");
        printResult(indexSearcher, query);
        indexSearcher.getIndexReader().close();
    }
    //全删除
    @Test
    public void testAllDelete() throws Exception{
        IndexWriter indexWriter = getIndexWriter();
        indexWriter.deleteAll();
        indexWriter.close();
    }
    //根据条件删除
    @Test
    public void testDelete() throws Exception{
        IndexWriter indexWriter = getIndexWriter();
        Query query = new TermQuery(new Term("fileName","sql"));
        indexWriter.deleteDocuments(query);
        indexWriter.close();
    }

    //修改
    @Test
    public void testUpdate() throws Exception{
        IndexWriter indexWriter = getIndexWriter();
        Document doc = new Document();
        doc.add(new TextField("fileN","嗷嗷的", Field.Store.YES));
        doc.add(new TextField("fileC","功夫大师", Field.Store.YES));
        indexWriter.updateDocument(new Term("fileName", "sql"), doc);
        indexWriter.close();
    }




    //查看标准分析器的分词效果
    @Test
    public void testTokenStream() throws Exception {
        // 创建一个分析器对象
        //Analyzer analyzer = new CJKAnalyzer();//二分法分词
        Analyzer analyzer = new IKAnalyzer();//可扩展 通过 ext.dic 配置新的分词
        //Analyzer analyzer = new StandardAnalyzer();//官方推荐分词器
        // 获得tokenStream对象
        // 第一个参数：域名，可以随便给一个
        // 第二个参数：要分析的文本内容
        String content ="我是一个中国人，我学习了数据结构，它是一个本书，我是一个人";
        TokenStream tokenStream = analyzer.tokenStream("test",content);
        // 添加一个引用，可以获得每个关键词
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        // 添加一个偏移量的引用，记录了关键词的开始位置以及结束位置
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        // 将指针调整到列表的头部
        tokenStream.reset();
        // 遍历关键词列表，通过incrementToken方法判断列表是否结束
        while (tokenStream.incrementToken()) {
            // 关键词的起始位置
            System.out.println("start->" + offsetAttribute.startOffset());
            // 取关键词
            System.out.println(charTermAttribute);
            // 结束位置
            System.out.println("end->" + offsetAttribute.endOffset());
        }
        tokenStream.close();
    }

    private IndexWriter getIndexWriter() throws Exception{
        Directory directory = FSDirectory.open(Paths.get("F:/temp/index"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        return new IndexWriter(directory,config);
    }
    private IndexSearcher getIndexSearcher() throws Exception{
        //1、创建一个Directory对象，也就是索引库存放的位置
        Directory directory = FSDirectory.open(Paths.get("F:/temp/index"));
        //2、创建一个IndexReader对象，需要指定Directory对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //3、创建一个IndexSearcher,需要指定IndexReader对象
        return new IndexSearcher(indexReader);
    }
    //执行查询结果
    public void printResult( IndexSearcher indexSearcher,Query query)throws Exception{
        //5、执行查询
        TopDocs topDocs = indexSearcher.search(query, 4);
        //6、返回查询结果，遍历结果并输出
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc: scoreDocs){
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            System.out.println(document.get("fileName"));
            System.out.println(document.get("fileSize"));
            System.out.println(document.get("filePath"));
            System.out.println(document.get("fileContext"));
        }
    }
}
