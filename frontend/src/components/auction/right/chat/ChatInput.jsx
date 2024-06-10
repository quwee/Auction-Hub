import React, {useState} from 'react';
import cl from "../../../../styles/ChatInput.module.css";
import SendIcon from "../../../../assets/send.svg";
import {useAuthContext} from "../../../../context/context";

const ChatInput = ({auctionComplete, sendMessageCallback, showToolTipCallBack}) => {

    const {user} = useAuthContext()
    const [message, setMessage] = useState('')

    const sendMessage = () => {
        if (!message) {
            return
        }
        if (!user || auctionComplete) {
            showToolTipCallBack()
            return
        }
        let isDelivered = sendMessageCallback(message)

        if (isDelivered) {
            setMessage('')
        }
    }

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            sendMessage()
        }
    }

    return (
        <div>
            <hr/>
            <div className={cl.container}>
                <div className={cl.input__container}>
                    <input
                        className={cl.send__input}
                        value={message}
                        onChange={e => setMessage(e.target.value)}
                        onKeyDown={handleKeyPress}
                        placeholder="Введите сообщение"
                        maxLength="300"/>
                </div>
                <div
                    className={cl.send__button}
                    onClick={sendMessage}>
                    <img
                        className={cl.send__icon}
                        src={SendIcon}
                        alt=""/>
                </div>
            </div>
        </div>
    );
};

export default ChatInput;