package com.li.mykotlinapp.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/************************************************************************
 *@Project: android
 *@Package_Name: io.ionic.starter.utils
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/1/6
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
public class RxShellTool {
    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    public static CommandResult execCmd(String command, boolean isRoot, boolean isNeedResultMsg) {
        List list = new ArrayList();
        list.add(command);
        return execCmd((ArrayList<String>) list, isRoot, isNeedResultMsg);
    }

    public static CommandResult execCmd(ArrayList<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.size() == 0) {
            return new CommandResult(result, null, null);
        }
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        DataOutputStream os = null;
        try {
            String cmd = "";
            if (isRoot) {
                cmd = COMMAND_SU;
            } else {
                cmd = COMMAND_SH;
            }
            process = Runtime.getRuntime().exec(cmd);
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null) {
                    continue;
                }
                os.write(command.getBytes());
                os.writeBytes(COMMAND_LINE_END);
                os.flush();
            }
            os.writeBytes(COMMAND_EXIT);
            os.flush();
            result = process.waitFor();
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String successTemp = "";
                String errorTemp = "";
                while ((successTemp = successResult.readLine()) != null) {
                    successMsg.append(successTemp);
                }

                while ((errorTemp = errorResult.readLine()) != null) {
                    errorMsg.append(errorTemp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
                successResult.close();
                errorResult.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            process.destroy();
        }
        return new CommandResult(result, successMsg.toString(), errorMsg.toString());
    }

    /**
     * 是否是在root下执行命令
     *
     * @param command 命令
     * @param isRoot  是否root
     * @return CommandResult
     */
    public static CommandResult execCmd(String command, boolean isRoot)  {
        List list = new ArrayList();
        list.add(command);
        return execCmd((ArrayList<String>) list, isRoot, true);
    }

    /**
     * 返回的命令结果
     */
    static class CommandResult {
        /**
         * 结果码
         */
        int result;

        /**
         * 成功的信息
         */
        String successMsg;

        /**
         * 错误信息
         */
        String errorMsg;

        CommandResult(int result) {
            this.result = result;
        }

        CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}
