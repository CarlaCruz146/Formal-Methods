package at.ac.tuwien.inso.sqm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class Nachrichten {

    public static final Locale LOCALE = Locale.ENGLISH;
    private static final Logger LOGGER =
            LoggerFactory.getLogger(Nachrichten.class);
    @Autowired
    private MessageSource messageSource;

    public String get(String path) {
        LOGGER.info("Received messages for " + path);
        return messageSource.getMessage(path, null, LOCALE);
    }

    public String msg(String path, Object... args) {
        LOGGER.info("Received messages for object and path " + path);
        return messageSource
                .getMessage(path, args, LocaleContextHolder.getLocale());
    }
}
