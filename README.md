# AndroidPark
##### 这个是通过AndroidStudio提交的智能车库系统的代码
  用户如何取车？第一要保证用户已经到了车库旁，第二车库的存放位置必须和用户一一对应。我们想到的是平时在手机App中买电影票，用于提前买好电影票，当到时间去电影院时，只需要输入密码或者扫描二维码，就能取出电影票，方便快捷。我们的车也就想电影票一样<br>
  
首先我们要开启权限
``` 
<uses-permission android:name="android.permission.INTERNET" />//网络访问权限
<uses-permission android:name="android.permission.CAMERA"/> //相机权限
<uses-permission android:name="android.permission.VIBRATE"/>//震动权限
```
整个工作流程是这样：机器放在车库的车辆出口处，用户在操作面板上可以输入密码，或者通过操作面板旁边的摄像头扫描二维码。<br>这时设备会向服务器发送验证信息指令，服务器验证这个密码存在数据库中时，找到对应的车辆信息，并返回给车库设备，其中包括汽车的车位。<br>设备接收服务器信息后就将这个位置的车辆运出。

### 扫描二维码的实现
#### 1、	二维码扫描布局：
是一个RelativeLayout，一共包括三部分，第一部分是扫描区域，实现SurfaceView接口，整个xml布局是一个surfaceView。铺满整个屏幕，上面是一个相对布局。其中，中间一块是一个正方形，用于提示用户将二维码对准这个区域就可以扫描，一部分是周围区域，周围区域分为四部分，上下左右包围surfaceview。最后一部分是capture_scan_line。用于扫描的时候在surfaceview中上下移动。<br>
##### 这里要提一下android动画：<br>
动画包括：帧动画（Frame Animation）和补间动画（Tweened Animation）<br>
第一种：帧动画：帧动画是最容易实现的一种动画，这种动画更多的依赖于完善的UI资源，他的原理就是将一张张单独的图片连贯的进行播放，就是将连续的图片轮番显示：
```
<?xml version="1.0" encoding="utf-8"?>
<animation-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:drawable="@drawable/a_0"
        android:duration="100" />
    <item
        android:drawable="@drawable/a_1"
        android:duration="100" />
    <item
        android:drawable="@drawable/a_2"
        android:duration="100" />
</animation-list>
android:duration,这是动画持续的时间,时间以毫秒为单位
```
```Java
protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        ImageView animationImg1 = (ImageView) findViewById(R.id.animation1);
        animationImg1.setImageResource(R.drawable.frame_anim1);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) animationImg1.getDrawable();
        animationDrawable1.start();
    }
```
第二种是补间动画又可以分为四种形式，分别是<br>
*alpha（淡入淡出）*
*translate（位移）*
*scale（缩放大小）*
*rotate（旋转）。*<br>
补间动画的实现，一般会采用xml 文件的形式；代码会更容易书写和阅读，同时也更容易复用。<br>

详细的以后再讲，这次我们使用的是translate位移。在用户扫描的时候，一条横线在surface上下移动，提示用户程序正在识别二维码。上下移动就是用到了动画的translate位移。上代码：
```Java
TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
        Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.9f);
animation.setDuration(4500);
animation.setRepeatCount(-1);//-1表示不限制次数(直到过期),
animation.setRepeatMode(Animation.RESTART);
scanLine.startAnimation(animation);
```
平移动画TranslateAnimation，继承父类	Animation<br>

构造方法<br>
```Java
public TranslateAnimation(int fromXType, float fromXValue, int toXType, float toXValue,
            int fromYType, float fromYValue, int toYType, float toYValue)
```
一共有8个参数：<br>
fromXType：动画开始前的X坐标类型。取值范围为ABSOLUTE（绝对位置）、RELATIVE_TO_SELF（以自身宽或高为参考）、RELATIVE_TO_PARENT（以父控件宽或高为参考）。<br>
fromXValue：动画开始前的X坐标值。当对应的Type为ABSOLUTE时，表示绝对位置；否则表示相对位置，1.0表示100%。<br>
toXType：动画结束后的X坐标类型。<br>
toXValue：动画结束后的X坐标值。<br>
fromYType：动画开始前的Y坐标类型。<br>
fromYValue：动画开始前的Y坐标值。<br>
toYType：动画结束后的Y坐标类型。<br>
toYValue：动画结束后的Y坐标值。<br>
https://blog.csdn.net/ruancoder/article/details/52355744<br>

分析一下就是动画开始前后的x，y坐标，并且说明了这些坐标的属性。属性共分为三种：<br>
*ABSOLUTE（绝对位置）*<br>
*RELATIVE_TO_SELF（以自身宽或高为参考）*<br>
*RELATIVE_TO_PARENT（以父控件宽或高为参考）*<br>
使用Animation调用。在这里用的是RELATIVE_TO_PARENT（以父控件宽或高为参考），最后要上下移到0.9f的位置。<br>
```
animation.setDuration(4500);//表示移动所需要的时间
animation.setRepeatCount(-1); //从上到下循环次数，这里设置的是动画重复执行的次数，而不是动画执行的次数。故动画执行的次数为动画重复执行的次数加1
        //Android系统默认每个动画仅执行一次，通过该方法可以设置动画执行多次。
```
SurfaceView https://blog.csdn.net/qq373036876/article/details/52252633	<br>
下面就是打开相机，扫描二维码和解析<br>

#### 2、	打开相机
1.检测相机硬件并获取访问<br>
2.建立一个Preview类：需要一个相机预览的类，继承 SurfaceView 类，并实现SurfaceHolder接口。<br>
　　3.建立预览的布局。<br>
　　4.为拍照建立监听。<br>
　　5.拍照并且存储文件。<br>
　　6.释放相机。<br>
　　因为相机是一个共享资源，所以应该被谨慎管理，这样应用之间才不会发生冲突。<br>
<br>
　　所以使用完相机之后应该调用 Camera.release()来释放相机对象。<br>

如果不释放，后续的使用相机请求（其他应用或本应用）都会失败。<br>
```
/** 设置相机预览为竖屏 */
camera.setDisplayOrientation(90);
```
CountDownLatch使用场景及分析。也就是说主线程在等待所有其它的子线程完成后再往下执行。<br>
await()//当前线程等待计数器为0 是继续进行。<br>
https://www.cnblogs.com/bqcoder/p/6089101.html<br>
一个控件在其父窗口中的坐标位置<br>

View.getLocationInWindow(int[] location)<br>
Location是长度为2的数组<br>
<br>
多次调用Handler<br>
当为你的应用程序创建一个进程时，它的主线程专门负责一个消息队列，负责管理顶级应用程序对象（活动，广播接收器等）以及它们创建的任何窗口。
<br>您可以创建自己的线程，并通过Handler与主应用程序线程进行通信。 <br>这是通过调用与之前相同的<em> post </ em>或<em> sendMessage </ em>方法完成的，但是通过您的新线程完成。 然后，给定的Runnable或Message将被安排在Handler的消息队列中，并在适当时进行处理。<br>


Android开发：实时处理摄像头预览帧视频------浅析PreviewCallback,onPreviewFrame，AsyncTask的综合应用<br>
https://blog.csdn.net/yanzi1225627/article/details/8605061


捕捉摄像头的视频进行像素识别，通过onPreviewFrame获得相机数据<br>

如何接受到Camera中的数据：<br>
1、	得到了自己定义的surfaceView，通过surfaceView的getHolder()方法得到SurfaceHolder<br>
2、	初始化Camera，得到Camera对象，调用setPreviewDisplay(SurfaceHolder); 将画面开始在surfaceView中显示<br>
3、	整个背景都是相机图画，我们要截取一块作为识别区域。开启捕捉指定区域的CaptureActivityHandler。在这里开启restartPreviewAndDecode();用于通知相机的onPreviewFrame中data数据要发到哪个handler中。这时定义PreviewCallback类继Camera.PreviewCallback<br>
重写onPreviewFrame方法，向DecodeHandler中的Handler发送相机data数据<br>
4、	在CaptureActivityHandler中开启一个线程，将接收到的UIActivity传给这个线程，开始解析数据<br>
5、	在线程中使用CountDownLatch(1);等待并发完成。并又起了一个线程，用于<br>
```
        Looper.prepare();
        handler = new DecodeHandler(activity, hints);
```
6、DecodeHandler是用了通过handlerMessage接收onPreviewFrame传来的信息，真正解析的线程。（实际上就是解析线程和照相机线程之间进行通信）获取byte[] data 转成取得灰度图。将灰度图转成BinaryBitmap解码。最终将解析的结果直接通过handler发给UI线程<br>
首先定义一个类实现Camera.PreviewCallback接口，然后在它的onPreviewFrame(byte[] data, Camera camera)方法中即可接收到每一帧的预览数据，也就是参数data。 <br>
然后使用setPreviewCallback()、setOneShotPreviewCallback或setPreviewCallbackWithBuffer()注册回调接口，<br>

在onPreviewFrame方法中将获取到的图片信息发送给DecodeHandler的handlerMessage，在这里调用解码算法，并将最后的解码结果通过message.sendToTarget();发送给UI线程。<br>
https://blog.csdn.net/u012950099/article/details/51804506<br>
二维码识别开源项目zxing的使用和源码分析<br>


