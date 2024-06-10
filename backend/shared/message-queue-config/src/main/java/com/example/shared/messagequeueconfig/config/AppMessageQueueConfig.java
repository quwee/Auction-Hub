package com.example.shared.messagequeueconfig.config;

import com.example.shared.dto.auction.AuctionCompleteDto;
import com.example.shared.dto.bid.BidRequestDto;
import com.example.shared.dto.bid.BidResponseDto;
import com.example.shared.dto.chat.ChatMessageRequestDto;
import com.example.shared.dto.chat.ChatMessageResponseDto;
import com.example.shared.dto.event.EmailRegisterEvent;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.shared.messagequeueconfig.constants.AppMessageQueueConstants.*;

@Configuration
@ComponentScan(basePackages = "com.example.shared.messagequeueconfig")
@EnableRabbit
public class AppMessageQueueConfig {

    @Bean
    public Queue bidPublishQueue() {
        return new Queue(BID_PUBLISH_Q_NAME);
    }

    @Bean
    public Queue bidProcessQueue() {
        return new Queue(BID_PROCESS_Q_NAME);
    }

    @Bean
    public Queue chatPublishQueue() {
        return new Queue(CHAT_PUBLISH_Q_NAME);
    }

    @Bean
    public Queue chatProcessQueue() {
        return new Queue(CHAT_PROCESS_Q_NAME);
    }

    @Bean
    public Queue auctionCloseQueue() {
        return new Queue(AUCTION_CLOSE_Q_NAME);
    }

    @Bean
    public Queue emailPublishQueue() {
        return new Queue(EMAIL_PUBLISH_Q_NAME);
    }

    @Bean
    public SimpleMessageConverter converter() {
        var converter = new SimpleMessageConverter();
        String bidRequestDtoClassName = BidRequestDto.class.getName();
        String bidResponseDtoClassName = BidResponseDto.class.getName();
        String chatMessageRequestClassName = ChatMessageRequestDto.class.getName();
        String chatMessageResponseClassName = ChatMessageResponseDto.class.getName();
        String auctionCompleteClassName = AuctionCompleteDto.class.getName();
        String emailRegisterEventClassName = EmailRegisterEvent.class.getName();
        converter.setAllowedListPatterns(List.of(bidRequestDtoClassName, bidResponseDtoClassName,
                chatMessageRequestClassName, chatMessageResponseClassName, emailRegisterEventClassName,
                auctionCompleteClassName, "java.time.*"));
        return converter;
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter());
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

}
