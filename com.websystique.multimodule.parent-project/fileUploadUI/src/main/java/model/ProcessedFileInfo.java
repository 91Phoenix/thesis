package model;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Greeting")
public class ProcessedFileInfo {

   
    private  String content;

    public ProcessedFileInfo()
    {}
    
    public ProcessedFileInfo( String content) {
       
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
