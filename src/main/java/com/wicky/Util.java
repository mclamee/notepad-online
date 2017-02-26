package com.wicky;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.domingosuarez.boot.autoconfigure.jade4j.JadeHelper;

/**
 * Created by domix on 08/10/14.
 */
@JadeHelper("util")
public class Util {
	@Autowired
	MessageSource messageSource;

	public String message(String code) {
		return messageSource.getMessage(code, null, "not found: '${code}'", getLocale());
	}

	public String formatNumber(BigDecimal number, String format) {
		DecimalFormatSymbols dcfs = new DecimalFormatSymbols();
		DecimalFormat decimalFormat = new DecimalFormat(format, dcfs);
		decimalFormat.setParseBigDecimal(true);
		return decimalFormat.format(number);
	}
	
}
