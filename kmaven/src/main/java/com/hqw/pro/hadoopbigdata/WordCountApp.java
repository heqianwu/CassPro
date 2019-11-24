package com.hqw.pro.hadoopbigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;

public class WordCountApp {
    static final String INPUT_PATH = "hdfs://chaoren:9000/hello";
    static final String OUT_PATH = "hdfs://chaoren:9000/out";

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI(INPUT_PATH), conf);
        Path outPath = new Path(OUT_PATH);
        if (fileSystem.exists(outPath)) {
            fileSystem.delete(outPath, true);
        }

        Job job = new Job(conf, WordCountApp.class.getSimpleName());

        // 1.1指定读取的文件位于哪里
        FileInputFormat.setInputPaths(job, INPUT_PATH);
        // 指定如何对输入的文件进行格式化，把输入文件每一行解析成键值对
        //job.setInputFormatClass(TextInputFormat.class);

        // 1.2指定自定义的map类
        job.setMapperClass(MyMapper.class);
        // map输出的<k,v>类型。如果<k3,v3>的类型与<k2,v2>类型一致，则可以省略
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // 1.3分区
        job.setPartitionerClass(org.apache.hadoop.mapreduce.lib.partition.HashPartitioner.class);
        // 有一个reduce任务运行
        job.setNumReduceTasks(1);

        // 1.4排序、分组

        // 1.5归约

        // 2.2指定自定义reduce类
        job.setReducerClass(MyReducer.class);
        // 指定reduce的输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // 2.3指定写出到哪里
        FileOutputFormat.setOutputPath(job, outPath);
        // 指定输出文件的格式化类
        //job.setOutputFormatClass(TextOutputFormat.class);

        // 把job提交给jobtracker运行
        job.waitForCompletion(true);
    }

    /**
     * KEYIN     即K1     表示行的偏移量
     * VALUEIN     即V1     表示行文本内容
     * KEYOUT     即K2     表示行中出现的单词
     * VALUEOUT 即V2        表示行中出现的单词的次数，固定值1
     */
    static class MyMapper extends
            Mapper<LongWritable, Text, Text, LongWritable> {
        protected void map(LongWritable k1, Text v1, Context context)
                throws java.io.IOException, InterruptedException {
            String[] splited = v1.toString().split("\t");
            for (String word : splited) {
                context.write(new Text(word), new LongWritable(1));
            }
        }

        ;
    }

    /**
     * KEYIN     即K2     表示行中出现的单词
     * VALUEIN     即V2     表示出现的单词的次数
     * KEYOUT     即K3     表示行中出现的不同单词
     * VALUEOUT 即V3     表示行中出现的不同单词的总次数
     */
    static class MyReducer extends
            Reducer<Text, LongWritable, Text, LongWritable> {
        protected void reduce(Text k2, java.lang.Iterable<LongWritable> v2s,
                              Context ctx) throws java.io.IOException,
                InterruptedException {
            long times = 0L;
            for (LongWritable count : v2s) {
                times += count.get();
            }
            ctx.write(k2, new LongWritable(times));
        }

        ;
    }
}
