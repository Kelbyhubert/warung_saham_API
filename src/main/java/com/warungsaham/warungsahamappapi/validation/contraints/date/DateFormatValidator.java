package com.warungsaham.warungsahamappapi.validation.contraints.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateFormatValidator implements ConstraintValidator<DateFormat,String> {

    private String pattern;

    @Override
    public void initialize(DateFormat dateFormat) {
        this.pattern = dateFormat.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setLenient(false);
        try {
            Date parseDate = simpleDateFormat.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }

    }
    

    

}
