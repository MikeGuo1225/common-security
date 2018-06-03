# common-security

**目前包含如下几大模块：**

````java
common-security-core
common-security-validate
common-security-browser
common-security-app
common-security-authorize
````

****

## common-security-validate

## 1、是什么？

**通用的验证码处理。基于SpringSecurity技术。高扩展、高可用、配置简单、灵活，支持两种模式：前后端不分离（session存储数据）和前后端分离（比如：web前后端分离或者APP形式，采取redis存储数据）。**

## 2、怎么用？

- **1、后续所说的所有的配置项都配置到`security.properties`文件中。**
- **2、导入jar包后，在你自己SpringBoot项目启动类上添加如下注解**

`@ComponentScan(basePackages = {"com.chentongwei.security", "你自己的包名"})`

## 3、有什么用？

**场景：**

我们的系统都会带有登录功能，而登录功能常用的无非就三种：表单登录、短信登录、第三方登录。

表单登录的话大多都会带有验证码功能，而验证码（主流的有图形验证码也有极验证）我们需要写接口生成，也需要写一些判断来验证是否正确，短信登录的话我们也需要验证短信验证码是否匹配等操作。还有URL安全问题，比如我不知道你密码，但是我却知道你的访问url，那么我直接敲url，应该会让我们跳转到登录页去等。

**问题：**

- 1、我们需要自己写验证码生成接口。
- 2、如果网站登录要极验证，发表评论要图形验证码，我们需要写两套验证码接口，还需要与极验证SDK做整合。
- 3、登录（或其他需要验证码的接口）的时候我们需要验证验证码是否为空、是否匹配、是否过期等等操作。
- 4、一些URL未登录的时候无法被访问，返回401没有权限状态码，我们需要作此限制。

**解答**

`common-security-validate`能完美解决以上全部问题：

- 1、直接调用`code/image`接口即可获取图形验证码，返回的是base64编码后的。Demo如下：

  ```javascript
  <img id="imageCode" src="" onclick="getImageCode()" />
  window.onload = function () {
      getImageCode();
  }
  function getImageCode() {
      $.ajax({
          url: "code/image",
          type: "get",
          /**
           * 为什么要在headers里传个deviceId参数？
           * 若你是前后端不分离（采取session共享数据），则无需传递此header里的参数。
           * 若你是前后端分离（采取redis共享数据），则需要在header里传递deviceId参数，
           * 若你是APP，则此参数建议取设备id，若你是web前后分离，则可以从后台获取一个然后放到缓存中，并放到此处。它标记着一个唯一的用户。
          */
          headers: {
              "deviceId": "123"
          },
          success: function (data) {
              $("#imageCode").attr("src", "data:image/jpeg;base64," + data);
          }
      });
  }
  ```

- 2、上面介绍了图形验证码，这里看下极验证：

  ​	2.1、首先去极验官网注册账号并创建项目，然后获取到id和key。

  ​	2.2、然后在配置文件里配置好，并直接调用`code/geetest`接口即可获取极验验证码。Demo如下：

  `security.properties`

  ```properties
  com.chentongwei.security.code.geetest.id=f4fac365b3ccb82ed68cc8c1ab6346f6
  com.chentongwei.security.code.geetest.key=c91ca7c31cf0d8a40c5cf5714624bfb2
  com.chentongwei.security.code.geetest.newfailback=true
  ```

  `html+js`

  ```javascript
  <div class="row cl">
      <div id="captcha" class="formControls col-xs-8 col-xs-offset-3">
          <p id="wait">正在加载验证码......</p>
      </div>
  </div>
  <button id="popup-submit">测试登录</button>
  
  $.ajax({
      // 获取id，challenge，success（是否启用failback）
      url: "code/geetest?t=" + (new Date()).getTime(), // 加随机数防止缓存
      type: "get",
      dataType: "json",
      /**
       * 为什么要在headers里传个deviceId参数？
       * 若你是前后端不分离（采取session共享数据），则无需传递此header里的参数。
       * 若你是前后端分离（采取redis共享数据），则需要在header里传递deviceId参数，
       * 若你是APP，则此参数建议取设备id，若你是web前后分离，则可以从后台获取一个然后放到缓存中，并放到此处。它标记着一个唯一的用户。
      */
      headers: {
          "deviceId": "123"
      },
      success: function (data) {
          console.log("data:" , data);
          // 使用initGeetest接口
          // 参数1：配置参数
          // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
          initGeetest({
              gt: data.gt,
              challenge: data.challenge,
              product: "popup", // 产品形式，包括：float，embed，popup。注意只对PC版验证码有效
              offline: !data.success // 表示用户后台检测极验服务器是否宕机，与SDK配合，用户一般不需要关注
          }, handlerPopup);
      }
  });
  // 代码详细说明
  var handlerPopup = function (captchaObj) {
      console.info(captchaObj)
      // 注册提交按钮事件，比如在登陆页面的登陆按钮
      $("#popup-submit").click(function () {
          // 此处省略在登陆界面中，获取登陆数据的一些步骤
  
          // 先校验是否点击了验证码
          var validate = captchaObj.getValidate();
          if (!validate) {
              alert('请先完成验证！');
              return;
          }
          // 提交验证码信息，比如登陆页面，你需要提交登陆信息，用户名和密码等登陆数据
          $.ajax({
              url: "login",
              type: "post",
              // dataType: "json",
              data: {
                  // 用户名和密码等其他数据，自己获取，不做演示
                  username:"13123",
                  password:"123456",
                  // 验证码数据，这些数据不用自己获取
                  // 这是二次验证所需的三个值
                  // 当然，你也可以直接设置验证码单独校验，省略其他信息
                  geetest_challenge: validate.geetest_challenge,
                  geetest_validate: validate.geetest_validate,
                  geetest_seccode: validate.geetest_seccode
              },
              headers: {
                  "deviceId": "123"
              },
              // 这里是正确返回处理结果的处理函数
              // 假设你就返回了1，2，3
              // 当然，正常情况是返回JSON数据
              success: function (result) {
                  alert("登陆成功！" + result);
              }
          });
      });
  
      // 将验证码加到id为captcha的元素里
      // 验证码将会在下面指定的元素中显示出来
      captchaObj.appendTo("#captcha");
      captchaObj.onReady(function () {
          $("#wait").hide();
      });
  };
  ```

- 3、校验全都封装好了，你只需要在配置文件里配置上需要验证码的url即可。Demo如下：

  ```properties
  # 需要图形验证码拦截的url
  com.chentongwei.security.code.image.url=/login
  # 需要极验验证码拦截的url
  com.chentongwei.security.code.geetest.url=/login, /hello
  ```

  > PS：在我们对如上配置的url进行访问的时候，会进行验证码的校验操作。
  >
  > 比如：我们在/login登录接口上做了验证码配置，则直接传递用户名密码请求login的话是会被拦截报错的，会提示验证码不能为空等字样。
  >
  > 具体拦截如下内容：
  >
  > ```
  > 验证码的值不能为空
  > 验证码不存在，请刷新页面重试
  > 验证码已过期
  > 验证码不匹配
  > ```

- 4、直接配置文件里面配置上哪些url无需权限即可，若不配置，则默认拦截全部url（意味着只有登录才可以访问）。

  ```properties
  # 无需登录即可访问的url
  com.chentongwei.security.authorize.permitUrls=/hello, /hello2
  ```

## 4、补充

### 4.1、配置项

**图形验证码（默认长度4、宽67、高23、有效期1分钟）**

```properties
# 图形验证码长度
com.chentongwei.security.code.image.length=6
# 图形验证码宽度
com.chentongwei.security.code.image.width=150
# 图形验证码高度
com.chentongwei.security.code.image.height=80
# 图形验证码过期时间(s)
com.chentongwei.security.code.image.expireIn=120
# 需要图形验证码拦截的url
com.chentongwei.security.code.image.url=/login
```

**极验证（默认有效期1分钟）**

```properties
com.chentongwei.security.code.geetest.id=f4fac365b3ccb82ed68cc8c1ab6346f6
com.chentongwei.security.code.geetest.key=c91ca7c31cf0d8a40c5cf5714624bfb2
com.chentongwei.security.code.geetest.newfailback=true
# 极验验证码过期时间(s)
com.chentongwei.security.code.geetest.expireIn=120
# 需要极验验证码拦截的url
com.chentongwei.security.code.geetest.url=/login,  /hello
```

**短信验证码（默认长度6、有效期1分钟）**

```properties
# 短信验证码长度
com.chentongwei.security.code.sms.length=6
# 短信验证码过期时间
com.chentongwei.security.code.sms.expireIn=120
# 需要短信验证码拦截的url
com.chentongwei.security.code.sms.url=/mobile
```

**前后分离配置（默认不分离，session存储）**

```properties
# redis为前后分离，验证码存储采用redis，默认不写是session的方式，前后不分离。
# 若采取的是redis方式，千万记得在验证码接口参数中像header里传递deviceId，这个在前面demo中有注释详细说明
com.chentongwei.security.code.repository=redis
```

**限制IP访问接口次数（默认不对任何接口做限制，若配了Url，则默认是3s内最多访问10次）**

```properties
# 需要设置同ip在一定时间内最多可访问同url几次的url路径
com.chentongwei.security.authorize.ipValidateUrl=/hello, /hello2
# 同ip在一定时间内最多可访问同url几次的时间配置（秒）
com.chentongwei.security.authorize.ipValidateSeconds=5
# 同ip在一定时间内最多可访问同url几次的次数配置
com.chentongwei.security.authorize.ipValidateCount=5
```

**URL权限配置**

```properties
# 无需登录即可访问的url
com.chentongwei.security.authorize.permitUrls=/hello,/hello2
# 若出现401状态码，则跳转到固定page
com.chentongwei.security.authorize.unAuthorizePage=/default-login.html
```

> PS：
>
> 1、默认拦截全部url
>
> 2、访问没权限的url，返回401状态码，若配置了unAuthorizePage，则跳转到unAuthorizePage页面，若没配置unAuthorizePage，则返回如下JSON：
>
> ```json
> {"code":401,"msg":"Unauthorized","data":null}
> ```

**REDIRECT / JSON（默认JSON）**

```properties
# REDIRECT/JSON 认证成功/失败后重定向到页面还是返回JSON
com.chentongwei.security.authentication.loginType=REDIRECT
# REDIRECT的方式，认证失败后跳转到的页面url（默认URL是及其不友好的），默认，此配置JSON方式无效
com.chentongwei.security.authentication.loginErrorPage=http://www.tucaole.cn
# REDIRECT的方式，认证成功后跳转到的页面url（默认URL是上一次访问的URL），此配置JSON方式无效
com.chentongwei.security.authentication.loginSuccessPage=http://www.tucaole.cn
```

### 4.2、扩展项

**图形验证码**

> PS：若你不喜欢我生成的图形验证码，比如嫌弃我的条纹太少了，样式太丑了神马的，完全可以自定义一个验证码接口，放心，即使是你自定义的，我的验证一样会生效。
>
> 自定义接口前提：
>
> 1、bean的id为`imageValidateCodeGenerator`
>
> 2、实现`com.chentongwei.security.validate.code.ValidateCodeGenerator`
>
> ```java
> @Component("imageValidateCodeGenerator")
> public class ImageValidateCodeGenerator implements ValidateCodeGenerator  {
>     
>     private final Logger logger = LoggerFactory.getLogger(getClass());
> 
>     @Override
>     public ValidateCode generate(ServletWebRequest request) {
>         logger.info("自定义的验证码生成逻辑....");
>         return new ValidateCode();
>     }
> }
> ```

**极验证验证码**

> PS：若你不喜欢我生成的极验验证码完全可以自定义一个验证码接口，放心，即使是你自定义的，我的验证一样会生效。
>
> 自定义接口前提：
>
> 1、bean的id为`geetestValidateCodeGenerator`
>
> 2、实现`com.chentongwei.security.validate.code.ValidateCodeGenerator`
>
> ```java
> @Component("geetestValidateCodeGenerator")
> public class GeetestValidateCodeGenerator implements ValidateCodeGenerator {
>     private final Logger logger = LoggerFactory.getLogger(getClass());
> 
>     @Override
>     public ValidateCode generate(ServletWebRequest request) {
>         logger.info("自定义的验证码生成逻辑....");
>         return new ValidateCode();
>     }
> }
> ```

**短信验证码**

> PS：我默认的短信验证码生成完全没用，我就在控制台输出了一串数字，因为这个短信平台的厂商不同，实现方式不同，所以我提供一个接口，你们自行实现自己对接的厂商即可。放心，即使是你自定义的，我的验证一样会生效。
>
> 自定义接口前提：
>
> 1、实现`com.chentongwei.security.validate.code.sms.SmsCodeSender`
>
> ```java
> public class MySmsCodeSender implements SmsCodeSender {
>     @Override
>     public void send(String mobile, String code) {
>         System.out.println("send.......");
>     }
> }
> ```

**认证成功/失败处理器**

**成功处理器**

> PS：
>
> 自定义成功处理器，前提只有一个，bean的id为`authenticationSuccessHandler`
>
> ```java
> @Component("authenticationSuccessHandler")
> public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
> 	
>     private Logger logger = LoggerFactory.getLogger(getClass());
>     
>     @Override
>     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
>         logger.info("验证成功！");
>     }
> }
> ```

**失败处理器**

> PS：
>
> 自定义失败处理器，前提只有一个，bean的id为`authenticationFailureHandler`
>
> ```java
> @Component("authenticationFailureHandler")
> public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
> 
>     private Logger logger = LoggerFactory.getLogger(getClass());
> 
>     @Override
>     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
>         logger.info("验证失败！");
>     }
> }
> ```

**UserDetailsService**

> PS：用过SpringSecurity的人一定知道UserDetailsService，我内置了一个DefaultUserDetailsService，没任何用处，唯一用途就是启动的时候若没有写UserDetailsService的实现，不会出错，但是大家一定要写UserDetailsService的实现，来完成自己的登录业务逻辑，我的只是为了启动不报错而生的！！

**SpringSecurity的核心配置**

> PS：我内置了一个叫`ValidateSecurityCoreConfig`的核心配置，里面只有一些基础的配置，比如：成功失败处理器，接口权限等。没有包含session、logout等等一堆东西，若我的满足不了你们的需求，则大可自定义。
>
> 自定义配置前提：
>
> 1、需要在配置文件里指定enable=false，缺省值是true
>
> ```properties
> # 自定义核心配置，false：自定义；true：采用我的默认配置，缺省值为true
> com.chentongwei.security.core.config.enable=false
> ```
>
> 这样就可以自定义SpringSecurity的配置了。 
>
> 2、自定义的SpringSecurity配置类：
>
> 建议至少包含我默认的`ValidateSecurityCoreConfig`类里的注入的bean，而你只增加注入的bean和修改configure方法，因为我注入的bean有成功失败处理器，验证码校验器，短信配置等，这些功能你都不用的话，那就别用此框架啦！！！
>
> 还有一个非常关键的是：
>
> ```java
> @Autowired
> private AuthorizeConfigManager authorizeConfigManager;
>
> // 一定要放到最后，是因为config方法里最后做了其他任何方法都需要身份认证才能访问。
> // 放到前面的话，后面在加载.antMatchers(getPermitUrls()).permitAll()的时候也会被认为无权限，
> // 因为前面已经做了其他任何方法都需要身份认证才能访问，SpringSecurity是有先后顺序的。
> authorizeConfigManager.config(http.authorizeRequests());
> ```
>
> 若我不想自定义配置类，我想在你这配置类里追加几个`ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry`类里的方法能行吗？
>
> 能行，在你项目中写个类实现`com.chentongwei.security.core.authorize.AuthorizeConfigProvider`即可，并且**指定@Order(Integer.MAX_VALUE - 100)**，其他什么都不用管。会自动生效！！！
>
> 比如
>
> ```java
> @Component
> @Order(Integer.MAX_VALUE - 100)
> public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {
>     @Override
>     public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
>         config.antMatchers("/hello2").hasRole("admin");
>     }
> }
> ```
>
> 还有一个需要注意的地方，就是再比如：如果我们既要自定义`config.antMatchers`又要自定义`config.anyRequest()`，这时候不能写到一个类（比如上面的`DemoAuthorizeConfigProvider`）里面，这时候要写两个类，一个是上面的那个类，还需要单独写一个类里面单独去定义`config.anyRequest()`，并且指定`@Order(Integer.MAX_VALUE)`，比如：
>
> ```java
> @Component
> @Order(Integer.MAX_VALUE)
> public class DemoAnyRequestAuthorizeConfigProvider implements AuthorizeConfigProvider {
>     @Override
>     public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
>         config.anyRequest().access("@rbacService.hasPermission(request,authentication)");
>     }
> }
> ```
>
> 总结注意如下四点：
>
> 1、实现AuthorizeConfigProvider接口。
>
> 2、若要配置非`config.anyRequest`（比如：`config.antMatchers`）的时候，需要指定`@Order(Integer.MAX_VALUE - 100)`；而配置`config.anyRequest`的时候，需要指定`@Order(Integer.MAX_VALUE)`。
>
> 3、自定义`config.anyRequest`和非`config.anyRequest`需要写两个不同的类，并指定不同的@Order。
>
> 4、除了内置的几个接口（如：验证码、登录等）不需要认证身份即可访问，其他的接口都是需要身份认证才可访问（`config.anyRequest().authenticated();`）。
