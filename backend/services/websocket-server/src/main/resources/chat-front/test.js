// init variables

const startBtn = document.querySelector("#start-chatting-btn");
const messageArea = document.querySelector('#messageArea');
const messageSendBtn = document.querySelector('#messageSendBtn');
const chatContainer = document.querySelector('#chatContainer');
const inputEl = document.querySelector('#messageInput');

let subscription;
let maxConnectionAttempts = 10;
let currentTry = 0;
const username = 'user123';

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8082/connect-ws',
    connectHeaders: {
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QiLCJ1c2VyLWlkIjoxLCJyb2xlIjoiUk9MRV9VU0VSIiwiaWF0IjoxNzEzMjIyMDMzLCJleHAiOjE3MTMyMjI5MzN9.QACJ5WHC7WWR81FdaHRzeMN6l0rTUJi42jjI9nLzBJo'
    },
    debug: function (str) {
        console.log(str);
    },
    beforeConnect: beforeConnect,
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


// functions

function createMessageElement(message) {
    let messageElement = document.createElement('li');
    messageElement.classList.add('message-element')
    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    return messageElement;
}

function beforeConnect() {
    currentTry++;
    console.log(`Connection attempt: ${currentTry}`);
    if(currentTry > maxConnectionAttempts) {
        console.log(`Exceeds max attempts (${maxConnectionAttempts}), will not try to connect now`);
        stompClient.deactivate();
    }
}

function connectToChat() {
    currentTry = 0;
    startBtn.classList.add('hidden');
    messageArea.classList.remove('hidden');
    chatContainer.classList.remove('hidden');
    // subscribe()
}

function subscribe() {
    subscription = stompClient.subscribe('/topic/messages', message => {
        if(message.body) {
            const payload = JSON.parse(message.body);
            let content = payload.content;
            let sender = payload.sender;
            let messageElement = createMessageElement(content);
            if(sender === username) {
                messageElement.style.justifyContent = 'end';
                messageElement.firstChild.style.backgroundColor = "rgb(128, 219, 255)";
                inputEl.value = "";
            }
            messageArea.appendChild(messageElement);
            messageArea.scrollTop = messageArea.scrollHeight;
        }
        else {
            alert('got empty message');
        }
    })
}

function sendMessage() {
    if(!stompClient.connected) {
        alert("Broker disconnected, can't send message.");
        return;
    }
    let content = inputEl.value.trim();
    if(content) {
        let message = {sender: username, content: content};
        stompClient.publish({
            destination: '/app/send-message',
            body: JSON.stringify(message)
        });
	    console.log(JSON.stringify(message));
    }
}


// add listeners

startBtn.addEventListener('click', connectToChat, true);
messageSendBtn.addEventListener('click', sendMessage, true);
document.addEventListener( 'keyup', e => {
    if( e.code === 'Enter' && !chatContainer.classList.contains('hidden')) {
        console.log(e.code);
        sendMessage(e);
    }
  }, true);