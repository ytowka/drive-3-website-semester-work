function onSubscribeClick(){
    let isSubscribed = $(this).text() === 'Отписаться';

    // Отправка AJAX-запроса и изменение состояния кнопки
    $.ajax({
        url: '/user/profile-api', // Ваш путь к API
        method: 'PATCH',
        data: {subscribe: !isSubscribed}, // Передача состояния подписки
        success: function() {
            if (isSubscribed) {
                $('#subscribeButton').text('Подписаться').css('background-color', '');
            } else {
                $('#subscribeButton').text('Отписаться').css('background-color', 'grey');
            }
        },
        error: function() {
            // Обработка ошибки при отправке запроса
        }
    });
}