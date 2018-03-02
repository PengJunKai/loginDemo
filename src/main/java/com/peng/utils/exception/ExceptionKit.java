package com.peng.utils.exception;

import java.lang.reflect.InvocationTargetException;

/**
 * 异常信息获取工具
 * Created by Rukiy on 2016-06-15
 */
public class ExceptionKit {


	public static String toString(Throwable e) {
		return buildExceptionString(e, 10);
	}

	private static String buildExceptionString(Throwable e, int lineNum) {
		StringBuilder buffer = new StringBuilder();
		if (e != null) {
			buffer.append(e.toString()).append(" , cause:").append(e.getCause()).append("\n");
			if (e instanceof InvocationTargetException) {
				e = ((InvocationTargetException) e).getTargetException();
			}

			StackTraceElement[] stc = e.getStackTrace();
			if (lineNum >= stc.length) {
				lineNum = stc.length;
			}
			for (int i = 0; i < lineNum; i++) {
				buffer.append("at ").append(stc[i].getClassName()).append(".").append(stc[i].getMethodName()).append(" (").append(stc[i].getLineNumber()).append(")\n");
			}
		}
		return buffer.toString();
	}
}
