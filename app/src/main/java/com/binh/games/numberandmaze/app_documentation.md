# Các package cơ bản trong app.
1. activity: Các activity của ứng dụng đều có ở đây (Ngoại trừ MainActivity).
2. core: Chứa logic chính của ứng dụng.
3. customview: ở đây có các view đặc biệt được sử dụng trong app. Hiện tại mới chỉ có SquareTextView.
4. fragment: ở đây bao gồm các fragment trong ứng dụng.
5. viewmodel: ở đây chứa tất cả các viewmodel của ứng dụng.
6. other: ở đây chứa một số nội dung khác, không có ở trên. Hiện tại có:
    - Inject.kt : Chứa các logic về việc khởi tạo các đối tượng (Dùng Kodein DI)
    - SwipeListener.kt: Chứa class dùng để implement các thao tác vuốt của người dùng (sẽ được dùng sau này trong các activity.)