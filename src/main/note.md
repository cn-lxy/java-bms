# 项目遇到的问题

## 1. 在Servlet中使用System.out.println() 输出中文的时候控制台乱码

1. 在IDEA设置-编辑器-文本编码-属性文件默认编码。改成 `UTF-8`
2. 编辑Tomcat服务器设置-服务器-虚拟机选项。添加: `-Dfile.encoding=utf-8`

## 2. flex布局问题

## 3. 使用Jquery发送Ajax请求

## 4. List集合的长度

## 5. js字符串占位符

## 6. js页面重载

## 7, url 命名规范

## 8. js 字符串去除左右空白字符

> String.trim()

## 9. JS如何将变量作为一个对象的Key

## 10. js alert() 与 confirm()

- alert() => 只具备提醒功能，只有确定功能
- confirm() => 有两个按钮，确定和取消，点击确定返回true，点击取消返回false

## 11. servlet 延时跳转 response.setHeader("Refresh", "[sec];url=[url]")

## 12. js设置input value

1. jquery: $('#name')[0].value = '[data]'
2. document.getElementById('#name').value = '[data]'

## 13. http 响应乱码 => 设置响应头编码格式

> resp.setHeader("content-type", "text/html;charset=UTF-8");

## 14. js 对象, Object, Object.keys

## 15. js 函数返回值 undefined

## 16. js在函数中的循环return只是跳出循环不会结束函数的执行

## 17. 表单设置取消自动填充，设置input输入长度

## 18. JSON格式: 键必须用双引号包裹，且在json字符串只能用双引号

## 19 js map 与 foreach

forEach(): 针对每一个元素执行提供的函数(executes a provided function once for each array element)。
map(): 创建一个新的数组，其中每一个元素由调用数组中的每一个元素执行提供的函数得来(creates a new array with the results of calling a provided function on every element in the calling array)。
