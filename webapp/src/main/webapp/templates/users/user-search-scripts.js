const userList = document.getElementById('user-list')

function loadUsers(
    url, query
){
    $.ajax({
        url: url,
        method: 'GET',
        data: {query: query},
        success: function (rawData){
            let data = JSON.parse(rawData)

            userList.innerHTML = ''
            data.users.forEach((user) =>{
                let userElement = document.createElement('div');
                userElement.classList.add("user-card")

                userElement.innerHTML = createUserCard(
                   user.avatarUrl,
                    user.name,
                    user.fullname,
                    user.profileUrl,
                )
                userList.appendChild(userElement);
            })
        }
    })
}

function createUserCard(
    avatarUrl, name, realName, url
){
    return`
<a href="${url}">
  <img src="${avatarUrl}" class="user-search-avatar">  
</a>
<div class="user-info">
    <a href="${url}" class="user-name">${name}</a>
    <p class="user-actual-name">${realName}</p>
</div>

    `
}

document.getElementById("search-query").addEventListener('input', function (evt){
    loadUsers(apiPath, evt.target.value)
    console.log("search: "+evt.target.value)
})

loadUsers(apiPath, "")