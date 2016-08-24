function connect() {
    var socket = new SockJS('/entry');
    return Stomp.over(socket);
}

function subscribeAddUser(stompClient) {
    console.log('### Subscribe AddUser###');

    stompClient.subscribe('/topic/user/add', function (res) {
        var userId = JSON.parse(res.body).userId;
        renderAddedUser(userId);
        console.log("[S] add user : " + userId);
    });
}

function renderAddedUser(userId) {
    var entry = document.getElementById("entry");
    var user = document.createElement("li");
    user.appendChild(document.createTextNode(userId));
    entry.appendChild(user);
}

function subscribeGameRequest(stompClient) {
    console.log('### Subscribe gameRequest ###');

    stompClient.subscribe('/user/queue/game-request', function (res) {
        var requestUserId = JSON.parse(res.body).userId;
        handleGameRequest(requestUserId);
    });
}

function handleGameRequest(requestUserId) {
    var accept = confirm(requestUserId + "님으로부터 게임 요청이 왔습니다.");
    if (accept) {
        console.log(requestUserId + "님과 게임 하러 이동합니다..")
        window.location.href = "/game-ground.v";
    } else {
        console.log(requestUserId + "님과 게임을 하지 않겠다고함..")
    }
}

function requestToGame(opponentUserId) {
    $.ajax({
        method: "POST",
        url: "/game-request",
        data: {user: me, opponent: opponentUserId}
    })
        .done(function (msg) {
            alert(opponentUserId + "에게 요청을 보냈습니다. 수락 여부를 기다려주세요.");
            console.log("게임 요청 처리 결과(" + me + "->" + opponentUserId + " : " + msg);
        });
}