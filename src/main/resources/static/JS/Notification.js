let stompClient = null;

function connect() {
    stompClient = Stomp.client('ws://localhost:8050/ws');

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // VERY IMPORTANT
        stompClient.subscribe('http://localhost:8050/user/queue/notifications', function (notification) {
            showNotification(notification.body);
        });
    });
}

function showNotification(message) {
    let box = document.getElementById("notificationBox");
    box.innerText = message;
    box.style.display = "block";

    setTimeout(() => {
        box.style.display = "none";
    }, 4000);
}

connect();