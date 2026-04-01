
let stompClient = null;

function connect() {
    let socket = new SockJS('/ws'); // must match your config
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // VERY IMPORTANT
        stompClient.subscribe('/user/queue/notifications', function (notification) {
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