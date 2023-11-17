const subscribersCountElem = document.getElementById("subscribers-count");

function onSubscribeClick(){


    $.ajax({
        url: `${contextPath}/api/subscribe`,
        method: 'POST',
        data: {
            userId: userId,
            subscribe: !isSubscribed,
        },
        success: function() {
            if (isSubscribed) {
                $('#subscribeButton').text('Подписаться').css('background-color', 'cornflowerblue');
            } else {
                $('#subscribeButton').text('Отписаться').css('background-color', 'grey');
            }
            if(isSubscribed){
                subscribersCount--
            }else{
                subscribersCount++
            }
            isSubscribed = !isSubscribed
            subscribersCountElem.innerText = `Подписчики: ${subscribersCount}`
        },
        error: function() {

        }
    });
}