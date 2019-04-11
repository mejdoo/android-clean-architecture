package com.mejdoo.clean

import com.mejdoo.clean.data.model.CommentEntity
import com.mejdoo.clean.data.model.PostEntity
import com.mejdoo.clean.data.model.UserEntity
import com.mejdoo.clean.domain.model.Comment
import com.mejdoo.clean.domain.model.Post
import com.mejdoo.clean.domain.model.User

val userEntity1 = UserEntity(
    1,
    "Leanne Graham",
    "Sincere@april.biz",
    "1-770-736-8031 x56442",
    "hildegard.org"
)
val userEntity2 = UserEntity(
    2,
    "Ervin Howell",
    "Shanna@melissa.tv",
    "010-692-6593 x09125",
    "anastasia.net"
)

val user1 = User(
    1,
    "Leanne Graham",
    "Sincere@april.biz",
    "1-770-736-8031 x56442",
    "hildegard.org"
)
val user2 =
    User(2, "Ervin Howell", "Shanna@melissa.tv", "010-692-6593 x09125", "anastasia.net")

val postEntity1 = PostEntity(
    1,
    1,
    "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
)
val postEntity2 = PostEntity(
    1,
    2,
    "qui est esse",
    "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
)

val post1 = Post(
    1,
    1,
    "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
    "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
)
val post2 = Post(
    1,
    2,
    "qui est esse",
    "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
)

val commentEntity1 = CommentEntity(
    1,
    1,
    "id labore ex et quam laborum",
    "Eliseo@gardner.biz",
    "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
)
val commentEntity2 = CommentEntity(
    1,
    2,
    "quo vero reiciendis velit similique earum",
    "Jayne_Kuhic@sydney.com",
    "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
)

val comment1 = Comment(
    1,
    1,
    "id labore ex et quam laborum",
    "Eliseo@gardner.biz",
    "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
)
val comment2 = Comment(
    1,
    2,
    "quo vero reiciendis velit similique earum",
    "Jayne_Kuhic@sydney.com",
    "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
)

