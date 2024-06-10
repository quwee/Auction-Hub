import React, {useEffect, useState} from 'react'
import NavbarWithMenu from "../components/NavbarWithMenu"
import cl from "../styles/Auction.module.css"
import LeftSide from "../components/auction/left/LeftSide"
import RightSide from "../components/auction/right/RightSide"
import AuctionService from "../API/service/AuctionService"
import {useParams} from "react-router-dom"
import Loader from "../components/ui/loader/Loader"
import {Client} from "@stomp/stompjs"
import {useAuthContext} from "../context/context"

const Auction = () => {

    const {id} = useParams()
    const {user} = useAuthContext()

    const [auctionDetails, setAuctionDetails] = useState()
    const [auction, setAuction] = useState()
    const [messages, setMessages] = useState([])
    const [auctionComplete, setAuctionComplete] = useState()

    const [stompClient, setStompClient] = useState()

    const [isNotFound, setIsNotFound] = useState(true)
    const [isLoading, setIsLoading] = useState(false)
    const [showTooltip, setShowTooltip] = useState(false)
    const [visible, setVisible] = useState(false)


    const showToolTipCallBack = () => {
        setVisible(true)
        setShowTooltip(true)
        setTimeout(() => {
            setShowTooltip(false)
            setTimeout(() => {
                setVisible(false)
            }, 500)
        }, 2000)
    }

    const fetchAuctionDetails = async () => {
        setIsLoading(true)
        let response = await AuctionService.getAuctionDetails(id)

        if (!response) {
            console.log('error', response)
            alert('Сервер не отвечает')
            setIsLoading(false)
            return
        }

        if (response.status === 200) {
            setAuctionDetails(response.data)
            setAuction(response.data.auction)
            setMessages(response.data.chatMessages)

            if (response.data.auctionComplete) {
                setAuctionComplete(response.data.auctionComplete)
            }

            setIsNotFound(false)
            console.log('AuctionDetails:', response.data)
        } else if (response.status === 404) {
            setIsNotFound(true)
        } else {
            console.log('error:', response)
            alert('Ошибка сервера')
        }
        setIsLoading(false)
    }

    useEffect(() => {
        const fetch = async () => {
            await fetchAuctionDetails()
        }
        fetch()

        if (!auctionComplete) {
            const client = new Client({
                brokerURL: 'ws://localhost:8082/connect-ws',
                connectHeaders: {
                    'Authorization': `Bearer ${localStorage.getItem('accessToken')}`
                },
                debug: (message) => {
                    console.log('stompClient: debug:', message)
                },
                onConnect: () => {
                    subscribeToAuction(client)
                    console.log('stompClient: connected to websocket server')
                },
                onWebSocketError: (event) => {
                    console.log('stompClient: websocket error:', event)
                },
                onWebSocketClose: (event) => {
                    console.log('stompClient: websocket connection is closed:', event)
                },
                heartbeatIncoming: 20000,
                heartbeatOutgoing: 20000,
            })

            setStompClient(client)
            client.activate()

            return () => {
                if (client.connected) {
                    client.deactivate()
                }
            }
        }
    }, [])

    const subscribeToAuction = (client) => {
        subscribeToChat(client)
        subscribeToBids(client)
        subscribeToClose(client)
    }

    const subscribeToChat = (client) => {
        let subscribeChatUrl = `/topic/auction.chat.${id}`
        client.subscribe(subscribeChatUrl, message => {
            console.log('stompClient: chat: message.body:', JSON.parse(message.body))
            if (message.body) {
                setMessages(prevMessages => [...prevMessages, JSON.parse(message.body)])
            } else {
                console.log('stompClient: chat: empty body')
            }
        })
    }

    const subscribeToBids = (client) => {
        let subscribeBidsUrl = `/topic/auction.bid.${id}`
        client.subscribe(subscribeBidsUrl, message => {
            if (message.body) {
                let bid = JSON.parse(message.body)
                console.log('stompClient: bid: message.body:', bid)

                setAuctionDetails(prevDetails => {
                    if (prevDetails) {
                        let updatedDetails = {
                            ...prevDetails,
                            bids: [...prevDetails.bids, bid],
                            auction: {
                                ...prevDetails.auction,
                                currentPrice: bid.price
                            }

                        }
                        console.log('updatedDetails:', updatedDetails)
                        return updatedDetails
                    }
                    console.log('prevDetails is empty')
                    return prevDetails
                })
            } else {
                console.log('stompClient: bid: empty body')
            }
        })
    }

    const subscribeToClose = (client) => {
        let subscribeChatUrl = `/topic/auction.complete.${id}`
        client.subscribe(subscribeChatUrl, message => {
            if (message.body) {
                console.log('stompClient: complete: message.body:', JSON.parse(message.body))
                setAuctionComplete(JSON.parse(message.body))
                client.deactivate()
            } else {
                console.log('stompClient: complete: empty body')
            }
        })
    }

    const sendMessageCallback = (message) => {
        if(!stompClient.connected) {
            alert("Сообщение не может быть отправлено.\nНе удалось подключиться к серверу.")
            return false
        }
        let chatMessageRequest = {
            'auctionId' : id,
            'senderId' : user.id,
            'content' : message
        }
        stompClient.publish({
            destination: '/app/send-message',
            body: JSON.stringify(chatMessageRequest)
        })
        return true
    }

    const placeBidCallback = (price) => {
        if(!stompClient.connected) {
            alert("Ставка не может быть сделана.\nНе удалось подключиться к серверу.")
            return false
        }
        let chatMessageRequest = {
            'auctionId' : id,
            'userId' : user.id,
            'price' : price
        }
        stompClient.publish({
            destination: '/app/send-bid',
            body: JSON.stringify(chatMessageRequest)
        })
        return true
    }

    const completeCallback = async () => {
        let response = await AuctionService.complete({'auctionId': id})

        if (!response) {
            console.log('error', response)
            alert('Сервер не отвечает')
            setIsLoading(false)
            return
        }

        if(response.status === 200) {
            console.log('completeCallback: response.data:', response.data)

        } else {
            console.log('error response:', response)
        }
        setIsLoading(false)
    }

    useEffect(() => {
        console.log('auctionComplete:', auctionComplete)
    }, [auctionComplete])

    return (
        <div>
            <NavbarWithMenu/>

            <div className={cl.tooltip__container}>
                <div
                    className={`${cl.tooltip} ${showTooltip ? '' : cl.hidden}`}
                    style={{display: visible ? 'block' : 'none'}}>
                    {auctionComplete ? 'Аукцион завершен' : 'Авторизуйтесь'}
                </div>
            </div>

            {isLoading
                ?
                <div className={cl.loader__container}>
                    <Loader/>
                </div>
                :
                !isNotFound
                    ?
                    <div className={cl.container}>
                        <LeftSide auction={auction}/>
                        <RightSide
                            messages={messages}
                            setMessages={setMessages}
                            sendMessageCallback={sendMessageCallback}
                            placeBidCallback={placeBidCallback}
                            completeCallback={completeCallback}
                            auctionDetails={auctionDetails}
                            showToolTipCallBack={showToolTipCallBack}
                            auctionComplete={auctionComplete}/>
                    </div>
                    :
                    <h1 className={cl.not__found}>
                        Аукцион не найден
                    </h1>
            }

        </div>
    )
}

export default Auction