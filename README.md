# StepView
一个整数递增、递减的简易控件
# 添加依赖
```
    compile 'ws.dyt.stepview:stepview:1.0'
```
# 使用
-   字符串
```xml
    <ws.dyt.stepview.view.StepTextView
        android:layout_width="150dp"
        android:layout_height="40dp"
        android:background="#a05ff4"
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
-   图形
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
-   属性支持
共有属性
```xml
    <!-- 正 负号控件背景色选择器 -->
    <attr name="step_minus_background_selector" format="reference" />
    <attr name="step_plus_background_selector" format="reference" />
    <!-- 正 负控件所占比例 -->
    <attr name="step_minus_weight" format="float" />
    <attr name="step_plus_weight" format="float" />

    <!-- 输入框文本配置属性 -->
    <attr name="input_is_input" format="boolean" /><!-- 输入框是否直接接受输入 -->
    <attr name="input_font_size" format="dimension" />
    <!-- 输入值域设置 -->
    <attr name="input_min" format="integer" />
    <attr name="input_max" format="integer" />
```
### StepTextView特有属性
```xml
    <!-- 正 负号控件文本配置属性 -->
    <attr name="step_font_size" format="dimension" />
    <!-- 正 负号控件文本颜色选择器 -->
    <attr name="step_minus_font_selector" format="reference" />
    <attr name="step_plus_font_selector" format="reference" />
    
    <attr name="step_minus_text" format="string" />
    <attr name="step_plus_text" format="string" />
```
### StepImageView特有属性
```xml
    <!-- 正 负控件图片选择器 -->
    <attr name="step_minus_drawable_selector" format="reference" />
    <attr name="step_plus_drawable_selector" format="reference" />
```