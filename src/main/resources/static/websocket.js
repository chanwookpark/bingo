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
    var entryUl = document.getElementById("userList");
    var userLi = document.createElement("li");
    var gameRequestButton = document.createElement("button");
    gameRequestButton.addEventListener("click", requestToGame);

    userLi.appendChild(document.createTextNode(userId));
    userLi.appendChild(gameRequestButton);
    entryUl.appendChild(userLi);
}

function subscribeGameRequest(stompClient) {
    var me = resolveMyUserId();

    console.log('### Subscribe gameRequest (' + me + ')###');

    stompClient.subscribe('/queue/game/request-to-' + me, function (res) {
        var json = JSON.parse(res.body);
        var requestUserId = json.requestUserId;

        handleGameRequest(requestUserId);
    });
}

function handleGameRequest(requestUserId) {
    var accept = confirm(requestUserId + "님으로부터 게임 요청이 왔습니다.");
    if (accept) {
        console.log(requestUserId + "님과 게임 하러 이동합니다..")
        window.location.href = "/game/approval?requestUserId=" + requestUserId;
    } else {
        console.log(requestUserId + "님과 게임을 하지 않겠다고함..")
    }
}

function subscribeGameApproval(stompClient) {
    var me = resolveMyUserId();

    console.log('### Subscribe game approval (' + me + ')###');

    stompClient.subscribe('/queue/game/approval-for-' + me, function (res) {
        var approvalUserId = res.body;

        handleGameApproval(approvalUserId);
    });
}

function handleGameApproval(approvalUserId) {
    var accept = confirm(approvalUserId + "님으로부터 게임 요청 승인났습니다. 게임으로 이동하시겠습니까?");

    if (accept) {
        console.log(approvalUserId + "님과 게임 하러 이동합니다..")
        window.location.href = "/game?opponentUserId=" + approvalUserId;
    } else {
        alert("게임 입장을 하지 않았습니다!(하자고 해놓고 왜 안하나요..??)");
    }
}

function resolveMyUserId() {
    var me = document.getElementById("myUserId").value;
    return me;
}

function requestToGame(opponentUserId) {
    $.ajax({
        method: "POST",
        url: "/game/request",
        data: {opponent: opponentUserId}
    })
        .done(function (msg) {
            alert(opponentUserId + "에게 요청을 보냈습니다. 수락 여부를 기다려주세요.");
            console.log("게임 요청 처리 결과(" + resolveMyUserId() + "->" + opponentUserId + " : " + msg + ")");
        });
}