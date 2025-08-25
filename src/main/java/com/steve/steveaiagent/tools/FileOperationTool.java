package com.steve.steveaiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.steve.steveaiagent.constants.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * 文件操作工具（提供文件读写功能）
 */
public class FileOperationTool {

    private final String FILE_DIR = FileConstant.FILE_SAVE_DIR + "/file";

    @Tool(description = "Read content from file")
    public String readFile(@ToolParam(description = "fileName to read") String fileName) {
        String filePath = FILE_DIR + fileName;
        try {
            return FileUtil.readUtf8String(filePath);
        } catch (Exception e) {
            return "Error Reading File: " + e.getMessage();
        }
    }

    @Tool(description = "Write content to file")
    public String writeFile(@ToolParam(description = "fileName to write") String fileName,
                            @ToolParam(description = "Content wrote to the file") String content) {
        String filePath = FILE_DIR + '/' + fileName;

        try {
            FileUtil.mkdir(FILE_DIR);
            FileUtil.writeUtf8String(content, filePath);
            return "Write File Success";
        } catch (Exception e) {
            return "Error Writing File: " + e.getMessage();
        }

    }
}
