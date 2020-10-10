<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      autocomplete="on"
      label-position="left"
    >
      <div class="title-container">
        <h3 class="title">OZO Mall 平台管理系统</h3>
      </div>

      <el-form-item prop="userName">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="userName"
          v-model="loginForm.userName"
          placeholder="账号"
          type="text"
          tabindex="1"
          autocomplete="on"
        />
      </el-form-item>
      <el-form-item prop="passWord">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.passWord"
          :type="passwordType"
          placeholder="密码"
          tabindex="2"
          autocomplete="on"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon
            :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"
          />
        </span>
      </el-form-item>
      <el-form-item prop="code">
        <div style="font-size:18px;" class="svg-container">
          <svg-icon icon-class="code" />
        </div>
        <el-input
          v-model="loginForm.code"
          placeholder="验证码"
          tabindex="3"
          autocomplete="on"
          @keyup.enter.native="handleLogin"
          maxlength="4"
        />
        <el-tooltip
          class="item"
          effect="dark"
          content="点击刷新验证码"
          placement="right"
        >
          <div class="verifyCode" @click="RefreCode">
            <img ref="verifyCode" src="/api/admin/verify/code" alt="" srcset="" />
          </div>
        </el-tooltip>
      </el-form-item>
      <el-button
        :loading="loading"
        type="primary"
        style="width:100%;margin-bottom:30px;"
        @click.native.prevent="handleLogin"
        >登录</el-button
      >
    </el-form>
    <div class="footer">
      闽ICP备2020018533号-1
    </div>
  </div>
</template>

<script>
import { login } from "@/api/sysManage";
import cryptoMd5 from "crypto-js/md5";
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        userName: "",
        passWord: "",
        code: ""
      },
      loginRules: {
        userName: [{ required: true, trigger: "blur", message: "请输入账号" }],
        passWord: [{ required: true, trigger: "blur", message: "请输入密码" }],
        code: [{ required: true, trigger: "blur", message: "请输入验证码" }]
      },
      passwordType: "password",
      loading: false,
      verifyCodeCount: 0
    };
  },
  mounted() {},
  methods: {
    // 刷新验证码
    RefreCode() {
      this.$refs.verifyCode.src =
        "/api/admin/verify/code?c=" + this.verifyCodeCount++;
    },
    // 明文显示密码
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
      } else {
        this.passwordType = "password";
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    // 登录
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          login({
            userName: this.loginForm.userName,
            passWord: cryptoMd5(encodeURI(this.loginForm.passWord)).toString(),
            code: this.loginForm.code
          })
            .then(response => {
              const { data } = response;
              console.log(response);
              if (data.code === 1) {
                console.log(data);
                this.$store.commit("user/SET_TOKEN", data.data.token);
                this.$router.push("/");
              } else {
                this.$message.error(data.msg);
                this.RefreCode();
              }
              this.loading = false;
            })
            .catch(error => {
              this.loading = false;
            });
        }
      });
    }
  }
};
</script>

<style lang="scss">
$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 60%;
    vertical-align: top;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
    vertical-align: top;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
  .verifyCode {
    display: inline-block;
    vertical-align: top;
    width: 130px;
    height: 45px;
    padding-top: 5px;
    cursor: pointer;
    img {
      width: 100%;
      height: 37px;
    }
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
  }

  .footer{
    width: 100%;
    height: 50px;
    line-height: 50px;
    position: fixed;
    bottom: 0;
    text-align: center;
    font-size: 12px;
    color: #5f6975;
    border-top: 1px solid #5f6975;
  }

  @media only screen and (max-width: 470px) {
    .thirdparty-button {
      display: none;
    }
  }
}
</style>
