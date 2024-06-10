const bidListContainer = document.querySelector("#bidListContainer")
const bidList = document.querySelector("#bidList")
const placeBidPriceInput = document.querySelector("#placeBidPriceInput")
const placeBidBtn = document.querySelector("#placeBidBtn")

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8082/connect-ws',
    connectHeaders: {
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QiLCJ1c2VyLWlkIjoxLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzEzMjIyMDMzLCJleHAiOjE3MTMyMjI5MzN9.QACJ5WHC7WWR81FdaHRzeMN6l0rTUJi42jjI9nLzBJo'
    },
    debug: (message) => {
        console.log(message)
    },
    onConnect: () => {
        subscribeToAuction()
        console.log('Connected to ws server')
    },
    onWebSocketError: (event) => {
        console.log('websocket error')
        console.log(event)
    },
    onWebSocketClose: (event) => {
        console.log('ws connection is closed')
        console.log(event)
    }
})

const currentUserId = 1

stompClient.activate()



function subscribeToAuction() {
    subscribeToBid()
    subscribeToChat()
    subscribeToComplete()
}

function subscribeToBid() {
    let subscribeBidUrl = '/topic/auction.bid.' + extractAuctionId()

    stompClient.subscribe(subscribeBidUrl, message => {
        if(message.body) {
            const bid = JSON.parse(message.body)

            console.log('Received message: ' + JSON.stringify(bid))

            let id = bid.id
            let username = bid.username
            let price = bid.price
            let placedAt = bid.placedAt

            let placeTime = extractPlaceTime(placedAt)

            let bidElement = createBidElement(username, price, placeTime)
            if(id === currentUserId) {
                bidElement.classList.add('bid-list-element-user')
            }
            bidList.appendChild(bidElement)
            bidList.scrollTop = bidList.scrollHeight
        }
        else {
            alert('got empty message')
        }
    })
}

function subscribeToComplete() {
    let subscribeCompleteUrl = '/topic/auction.complete.' + extractAuctionId()

    stompClient.subscribe(subscribeCompleteUrl, message => {
        if(message.body) {
            const auctionComplete = JSON.parse(message.body)

            console.log('Received message: ' + JSON.stringify(auctionComplete))

            let winnerId = auctionComplete.winnerId
            let totalPrice = auctionComplete.totalPrice
            let endDate = auctionComplete.endDate


        }
        else {
            alert('got empty message')
        }
    })
}

function sendMessage() {
    if(!stompClient.connected) {
        alert("Broker disconnected, can't send message.")
        return
    }
    let price = parseInt(placeBidPriceInput.value.trim())

    let bid = {
        auctionId: extractAuctionId(),
        userId: currentUserId,
        price: price
    }

    stompClient.publish({
        destination: '/app/send-bid',
        body: JSON.stringify(bid)
    })

    placeBidPriceInput.value = 1
}

function extractPlaceTime(placedAt) {
    let placedAtDate = new Date(placedAt)

    let hours = placedAtDate.getHours()
    let minutes = placedAtDate.getMinutes()

    return `${hours}:${minutes}`
}

function createBidElement(username, price, placedTime) {
    let listElement = document.createElement('div')
    let usernameElement = document.createElement('div')
    let priceElement = document.createElement('div')
    let placeTimeElement = document.createElement('div')

    listElement.classList.add('bid-list-element')
    usernameElement.classList.add('bid-list-element-username')
    priceElement.classList.add('bid-list-element-price')
    placeTimeElement.classList.add('bid-list-element-place-time')

    let usernameTextNode = document.createTextNode(username)
    let priceTextNode = document.createTextNode(price)
    let placeTimeTextNode = document.createTextNode(placedTime)

    usernameElement.appendChild(usernameTextNode)
    priceElement.appendChild(priceTextNode)
    placeTimeElement.appendChild(placeTimeTextNode)

    listElement.appendChild(usernameElement)
    listElement.appendChild(priceElement)
    listElement.appendChild(placeTimeElement)

    return listElement
}





function extractAuctionId() {
    /*let url = window.location.pathname
    return url.split('/').pop()*/

    return '1'
}


placeBidBtn.addEventListener('click', sendMessage)