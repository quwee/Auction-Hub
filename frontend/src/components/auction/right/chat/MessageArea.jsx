import React, {useEffect, useRef, useState} from 'react'
import cl from "../../../../styles/MessageArea.module.css"
import moment from "moment";
import {DATE_TIME_PATTERN, TIME_PATTERN} from "../../../../util/constants";
import {useAuthContext} from "../../../../context/context";

const MessageArea = ({messages}) => {

    const {user} = useAuthContext()
    const containerRef = useRef()

    const scrollToBottom = () => {
        if (containerRef.current) {
            containerRef.current.scrollTop = containerRef.current.scrollHeight
        }
    }

    useEffect(() => {
        scrollToBottom()
    }, [messages])

    return (
        <div className={cl.container} ref={containerRef}>
            {messages.length !== 0
                ?
                messages.map((m, id) =>
                    !user || m.senderId !== user.id
                        ?
                        <div className={cl.full__message__container} key={id}>
                            <div className={cl.author__image__container}>
                                {m.firstName.charAt(0)}{m.lastName.charAt(0)}
                            </div>
                            <div className={cl.message__container}>
                                <div className={cl.message__author}>
                                    {`${m.firstName} ${m.lastName}`}
                                </div>
                                <div className={cl.message__text__container}>
                                    <div className={cl.message__content__container}>
                                        <span>{m.content}</span>
                                    </div>
                                    <div className={cl.message__time__container}>
                                        <span>{moment(m.sentAt).format(TIME_PATTERN)}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        :
                        <div className={cl.full__user__message__container} key={id}>
                            <div className={cl.user__message__container}>
                                <div className={cl.message__author}>
                                    {`${m.firstName} ${m.lastName}`}
                                </div>
                                <div className={cl.message__text__container}>
                                    <div className={cl.message__content__container}>
                                        <span>{m.content}</span>
                                    </div>
                                    <div className={cl.message__time__container}>
                                        <span>{moment(m.sentAt).format(TIME_PATTERN)}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                )
                :
                ''
            }
        </div>
    )
}

export default MessageArea