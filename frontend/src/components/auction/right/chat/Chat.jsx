import React, {useEffect, useState} from 'react';
import cl from "../../../../styles/Chat.module.css";
import ChatIcon from "../../../../assets/chat.svg";
import ChatInput from "./ChatInput";
import MessageArea from "./MessageArea";

const Chat = ({messages, auctionComplete, sendMessageCallback, showToolTipCallBack}) => {

    const [isOpen, setIsOpen] = useState(false)

    const openChat = () => {
        setIsOpen(true)
    }

    const closeChat = () => {
        setIsOpen(false)
    }

    return (
        <div>
            <img
                className={cl.chat__icon}
                hidden={isOpen}
                onClick={openChat}
                src={ChatIcon}
                alt=""/>
            <div
                className={cl.container}
                hidden={!isOpen}>
                <span
                    className={cl.close__button}
                    hidden={isOpen}
                    onClick={closeChat}>
                    &#x2716;
                </span>
                <div className={cl.content__container}>
                    <MessageArea messages={messages}/>
                    <ChatInput
                        auctionComplete={auctionComplete}
                        sendMessageCallback={sendMessageCallback}
                        showToolTipCallBack={showToolTipCallBack}/>
                </div>
            </div>
        </div>
    );
};

export default Chat;