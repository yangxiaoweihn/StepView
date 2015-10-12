## 控件简介
    一个提供整数递增、递减的简易控件
## 添加依赖
```
    compile 'ws.dyt.stepview:stepview:1.0'
```
## 使用
-   文本形式递增 递减控件
```xml
    <ws.dyt.stepview.view.StepTextView
        android:layout_width="150dp"
        android:layout_height="40dp"
        app:step_font_size="18sp"
        app:step_minus_background_selector="@drawable/step_minus_background_selector"
        app:step_minus_font_selector="@drawable/step_minus_font_selector"
        app:step_minus_text="-"
        app:step_plus_background_selector="@drawable/step_plus_background_selector"
        app:step_plus_font_selector="@drawable/step_plus_font_selector"
        app:step_plus_text="+"
        app:step_minus_weight="1"
        app:step_plus_weight="1"
        app:input_min="5"
        app:input_max="10"
        app:input_is_input="true"
        />
```
-   图标形式递增 递减控价
```xml
    <ws.dyt.stepview.view.StepImageView
        android:layout_marginTop="10dp"
        android:layout_width="150dp"
        android:layout_height="40dp"
        app:step_minus_background_selector="@drawable/step_minus_background_selector"
        app:step_plus_background_selector="@drawable/step_plus_background_selector"
        app:step_minus_drawable_selector="@drawable/step_minus_image_selector"
        app:step_plus_drawable_selector="@drawable/step_plus_image_selector"
        app:input_min="5"
        app:input_max="10"
        />
```
### 属性支持
-    共用属性
```xml
    <!-- 递增 递减控件背景色选择器 -->
    <attr name="step_minus_background_selector" format="reference" />
    <attr name="step_plus_background_selector" format="reference" />
    <!-- 递增 递减控件所占比例 -->
    <attr name="step_minus_weight" format="float" />
    <attr name="step_plus_weight" format="float" />

    <!-- 输入框文本配置属性 -->
    <attr name="input_is_input" format="boolean" /><!-- 输入框是否直接接受输入 -->
    <attr name="input_font_size" format="dimension" />
    <!-- 输入值域设置 -->
    <attr name="input_min" format="integer" />
    <attr name="input_max" format="integer" />
```
- *StepTextView*（文本）控件特有属性
```xml
    <!-- 递增 递减控件文本配置属性 -->
    <attr name="step_font_size" format="dimension" />
    <!-- 递增 递减控件文本颜色选择器 -->
    <attr name="step_minus_font_selector" format="reference" />
    <attr name="step_plus_font_selector" format="reference" />
    
    <attr name="step_minus_text" format="string" />
    <attr name="step_plus_text" format="string" />
```
- *StepImageView*（图标）控件特有属性
```xml
    <!-- 递增 递减控件图片选择器 -->
    <attr name="step_minus_drawable_selector" format="reference" />
    <attr name="step_plus_drawable_selector" format="reference" />
```

- `Selector`类属性使用方法
    <p>
        上述属性中，凡是`xxx_selector`结尾表示的属性，都需要遵循下列写法才会起到相应作用。<br/>
        以下selector文件存在于res/drawable目录中。
    </p>
```xml
    <?xml version="1.0" encoding="utf-8"?>
    <selector xmlns:android="http://schemas.android.com/apk/res/android">
        <!-- 以下item节点中，一般情况下drawable属性都可用使用color属性替代 -->
        <!-- 控件可用、按下状态 -->
        <item android:state_pressed="true" android:state_enabled="true" android:drawable="@color/colorAccent" />
        <!-- 控件可用、正常状态 -->
        <item android:state_pressed="false" android:state_enabled="true" android:drawable="@color/colorPrimary" />
        <!-- 控件不可用状态 -->
        <item android:state_enabled="false" android:drawable="@color/colorGray" />
    </selector>
```

### 添加监听器
- <p>控件通过属性`input_min`、`input_max`设置值域范围后，可以通过设置以下监听器来监听数据的变化，设置方式为
    StepTextView stepTextView = (StepTextView) findViewById(R.id.stepTextView);
    stepTextView.setOnStepChangeListener(new StepTextView.IOnStepChangeListener() {
        @Override
        public void onStepChanged(int step, int min, int max) {
        }
    
        @Override
        public void onError() {
        }
    });
     </p>
```java
    /**
     *数据变动监听器
     */
    public interface IOnStepChangeListener{
        /**
         * @param step  变动数据
         * @param min   设置的最小值
         * @param max   设置的最大值
         */
        void onStepChanged(int step, int min, int max);

        /**
         *数据变动异常时回调
         */
        void onError();
    }
```