const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8082/connect-ws',
    connectHeaders: {
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QiLCJ1c2VyLWlkIjoxLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzEzMjIyMDMzLCJleHAiOjE3MTMyMjI5MzN9.QACJ5WHC7WWR81FdaHRzeMN6l0rTUJi42jjI9nLzBJo'
    },
    debug: str => {
        console.log(str);
    },
    onConnect: () => {
        console.log('Connected to ws server')
    },
    onWebSocketClose: () => {
        stompClient.reconnectDelay = 200 * Math.pow(2, currentTry);
        console.log(`Exponential back off - next connection attempt in ${stompClient.reconnectDelay}ms`);
    },
    onWebSocketError: (event) => {
        console.log('websocket error');
        console.log(event);
    },
    onWebSocketClose: (event) => {
        console.log('ws connection is closed');
        console.log(event);
    },
});

stompClient.activate();

function subscribe() {
    subscription = stompClient.subscribe('/topic/messages', message => {
        console.log('Message received')
        if(message.body) {
            const payload = JSON.parse(message.body);
            console.log(payload)
        }
        else {
            alert('got empty message');
        }
    })
}