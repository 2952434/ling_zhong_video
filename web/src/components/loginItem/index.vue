<template>
  <div class="loginDialog">
    <div class="left">
      <img src="../../assets/images/logo.png" alt="">
      <div class="left-text">
        <p>凌 众 短 视 频</p>
        <span class="oneWord">--发现你的热爱--</span>
      </div>
    </div>
    <div class="right">
      <div class="right-text">
        用 户 登 录
      </div>
      <!-- 邮箱密码登录 -->
      <el-form :model="loginByPassword" status-icon :rules="rules" ref="ruleFormRef">
        <el-form-item label="邮箱" prop="email" style="margin: 5px 0 40px 0;">
          <el-input v-model="loginByPassword.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginByPassword.password" show-password placeholder="请输入密码" type="password"></el-input>
        </el-form-item>
        <div class="loginByPasswordButton">
          <LoginButton @click="passwordLogin(ruleFormRef)">登录</LoginButton>
        </div>
      </el-form>
    </div>
  </div>
  <div class="mark">

  </div>
</template>

<script setup>
import { ElMessage } from 'element-plus';
import { ref, reactive } from 'vue'
// 引入好看的按钮
import LoginButton from './loginButton/index.vue'
const ruleFormRef = ref()
// 用户登录信息收集
const loginByPassword = reactive({
  email: '',
  password: ''
})
// 验证邮箱
const checkEmail = (rule, value, callback) => {
  if (value === '') {
    ElMessage.warning('请输入邮箱')
    callback(new Error('请输入邮箱'))
  } else {
    // 验证邮箱正则表达式
    const regEmail = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
    if (!regEmail.test(value)) {
      ElMessage.warning('请输入正确格式的邮箱')
      callback(new Error('请输入正确邮箱'))
    }
    callback()
  }
}
// 验证密码
const checkPassword = (rule, value, callback) => {
  if (value === '') {
    ElMessage.warning('请输入密码')
    callback(new Error('请输入密码'))
  } else {
    if (value.length < 6) {
      ElMessage.warning('密码长度应在6位以上')
      callback(new Error('密码长度应在6位以上'))
    } else {
      callback()
    }
  }
}
// 表单验证
const rules = reactive({
  email: [
    { validator: checkEmail, trigger: "blur" },
  ],
  password: [
    { validator: checkPassword, trigger: 'blur' }
  ]
})

// 账号密码登录事件
const passwordLogin = (formEl) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!')
      return false
    }
  })
}
</script>

<style scoped lang='scss'>
.loginDialog {
  position: absolute;
  width: 60%;
  height: 50%;
  background-color: black;
  top: 50%;
  left: 50%;
  z-index: 100;
  transform: translate(-50%, -50%);
  border-radius: 10px;
  display: flex;

  .left {
    width: 50%;
    padding: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;

    img {
      width: 90%;
      transform: translateY(-50px);
    }

    .left-text {
      font-size: 30px;
      font-family: '宋体';
      text-align: center;
      transform: translateY(-35px);

      .oneWord {
        font-size: 15px;
      }
    }
  }

  .right {
    width: 50%;
    padding: 20px;
    padding-top: 50px;

    .right-text {
      text-align: center;
      font-size: 25px;
      margin-bottom: 30px;
    }

    :deep(.el-input) {
      border: none;
    }

    :deep(.el-input__wrapper) {
      background-color: black !important;

      box-shadow: none;
      border-bottom: solid white 1px;
    }

    .loginByPasswordButton {
      width: 100%;
      display: flex;
      justify-content: center;
      margin-top: 50px;
    }
  }
}

.mark {
  width: 100vw;
  height: 100vh;
  z-index: 99;
  background-color: rgba(0, 0, 0, .5);
  position: absolute;
  top: 0;
  left: 0%;
}
</style>