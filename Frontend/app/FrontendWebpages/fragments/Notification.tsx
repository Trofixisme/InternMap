import { Client } from '@stomp/stompjs';
// import SockJS from 'sockjs-client';

let client: Client | null = null;

export function notification() {
    if (client && client.active) {
        return;
    }

    const token = localStorage.getItem("creditentialsKey");

    client = new Client({
        // webSocketFactory: () => new SockJS('http://localhost:8050/ws'),
        webSocketFactory: () => new WebSocket('ws://localhost:8050/ws'),
        connectHeaders: token ? {
            'Authorization': `Bearer ${token}`
        } : {},
        debug: (str) => {
            console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    });

    client.onConnect = (frame) => {
        console.log('Connected: ' + frame);
        client?.subscribe('/user/queue/notifications', (message) => {
            console.log('Received notification: ' + message.body);
            showNotification(message.body);
        });
    };

    client.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    client.activate();
}

function showNotification(message: string) {
    const box = document.getElementById("notificationBox");
    if (box) {
        box.innerText = message;
        box.style.display = "block";

        setTimeout(() => {
            box.style.display = "none";
        }, 5000);
    }
}