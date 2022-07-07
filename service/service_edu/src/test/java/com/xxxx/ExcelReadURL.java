package com.xxxx;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xxxx.eduservice.entity.SubjectData;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class ExcelReadURL {
    public static void main(String[] args) {

        try {
            InputStream inputStream = new URL("https://educhong.oss-cn-qingdao.aliyuncs.com/subject.xlsx").openStream();

            EasyExcel.read(inputStream, SubjectData.class, new AnalysisEventListener<SubjectData>() {
                         public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                             System.out.println(headMap);
                         }

                         @Override
                         public void invoke(SubjectData excelSubjectData, AnalysisContext analysisContext) {
                             System.out.println(excelSubjectData);
                         }

                         @Override
                         public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                         }
                     })
                     .sheet()
                     .doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
