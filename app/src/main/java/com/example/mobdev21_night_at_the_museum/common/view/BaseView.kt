package com.example.mobdev21_night_at_the_museum.common.view

interface BaseView {
    /**
     * Gọi trước khi khởi tạo view
     */
    fun onPrepareInitView() {}

    /**
     * Gọi khi gán instance cho data binding
     */
    fun onInitBinding() {}

    /**
     * Gọi sau khi đã khởi tạo view
     */
    fun onInitView()

    /**
     * Gọi sau khi view được khởi tạo, quan sát dữ liệu
     */
    fun onObserverViewModel() {}

    /**
     * Gọi khi destroy view (khi sử dụng replace fragment) để tránh gây ra leak nếu như khởi tạo
     * các đối tượng từ onViewCreated() trở đi
     */
    fun onCleaned() {}
}
