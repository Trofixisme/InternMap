import { Client } from '@stomp/stompjs';
import {toast} from "@heroui/react";
// import SockJS from 'sockjs-client';

let client: Client | null = null;

export function notification() {
    if (client && client.active) {
        return;
    }

    client = new Client({
        //Never using SockJS again... WHY DID WE USE IT IN THE FIRST PLACE????
        // webSocketFactory: () => new SockJS('http://localhost:8050/ws'),
        webSocketFactory: () => new WebSocket('http://localhost:8050/websocket/ws'),
        debug: (str) => {
            console.log(str);
        },
    });

    client.onConnect = (frame) => {
        console.log("If you're reading this, Have a great day!");
        console.log('Connected: ' + frame);
        client?.subscribe('/user/queue/notifications', (message) => {
            console.log('You got mail! -> ' + message.body);
            toast("You got mail!", {
                actionProps: {
                    children: "Dismiss",
                    onPress: () => toast.clear(),
                    variant: "tertiary",
                },
                indicator: <img src="/images/assets/bell.fill@4x.png" alt="Bell" width={15} height={15}/>,
                description: message.body,
                variant: "default",
            })
        });
    };

    client.onStompError = (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
    };

    client.activate();
}