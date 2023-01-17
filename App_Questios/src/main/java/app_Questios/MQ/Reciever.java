package app_Questios.MQ;

import app_Questios.Model.Category;
import app_Questios.Model.Questions;
import app_Questios.Services.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Component
public class Reciever {
    //private static Logger log = LoggerFactory.getLogger(Reciever.class);

    @Autowired
    CategoryService categoryService;

    @RabbitListener(queues = "${jsa.rabbitmq.queue}", containerFactory = "jsaFactory")
    public void recievedMessage(Questions questions) {

        //log.info("GET /Questions/ 'recieved Questions from mq'");

        Category category = questions.getCategory();

        categoryService.Save(category, questions);


    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory jsaFactory(ConnectionFactory connectionFactory,
                                                           SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}