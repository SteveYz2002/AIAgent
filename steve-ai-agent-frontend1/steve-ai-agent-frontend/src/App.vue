<script setup>
import HelloWorld from './components/HelloWorld.vue'
import TheWelcome from './components/TheWelcome.vue'
import AppLogo from './components/AppLogo.vue'
import AppFooter from './components/AppFooter.vue'
</script>

<template>
  <div id="app">
    <nav class="nav">
      <div class="nav-container">
        <AppLogo />
        <div class="nav-links">
          <router-link to="/">
            <span class="nav-icon">&lt;/&gt;</span>首页
          </router-link>
          <router-link to="/love-master">
            <span class="nav-icon">❤️</span>恋爱补给站AI
          </router-link>
          <router-link to="/super-agent">
            <span class="nav-icon">🧠</span>AI 超级智能体
          </router-link>
        </div>
        <div class="cyber-ui-element"></div>
      </div>
    </nav>
    <router-view />
    <AppFooter />
    <div class="cyber-footer">
      <div class="cyber-line"></div>
    </div>
  </div>
</template>

<script>
import AppLogo from './components/AppLogo.vue'
import AppFooter from './components/AppFooter.vue'

export default {
  name: 'App',
  components: {
    AppLogo,
    AppFooter
  }
}
</script>

<style scoped>
.nav-links {
  display: flex;
  gap: 15px;
}

.nav-icon {
  display: inline-block;
  margin-right: 5px;
  font-size: 0.9em;
}

.cyber-ui-element {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  width: 30px;
  height: 30px;
  border: 1px solid var(--primary-color);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  animation: rotate 10s linear infinite;
}

.cyber-ui-element::before {
  content: '';
  position: absolute;
  width: 10px;
  height: 10px;
  background-color: var(--primary-color);
  border-radius: 50%;
  animation: pulse 2s ease-in-out infinite;
}

.cyber-ui-element::after {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  border: 1px solid var(--primary-color);
  animation: pulse-out 4s linear infinite;
}

@keyframes pulse-out {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  100% {
    transform: scale(1.5);
    opacity: 0;
  }
}

@keyframes rotate {
  0% { transform: translateY(-50%) rotate(0deg); }
  100% { transform: translateY(-50%) rotate(360deg); }
}

.cyber-footer {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 5px;
  z-index: 100;
  pointer-events: none;
}

.cyber-line {
  height: 2px;
  background: linear-gradient(to right, transparent, var(--primary-color), transparent);
  position: relative;
}

.cyber-line::before,
.cyber-line::after {
  content: '';
  position: absolute;
  width: 10px;
  height: 10px;
  background-color: var(--primary-color);
  border-radius: 50%;
  top: -4px;
  animation: pulse 2s infinite alternate;
}

.cyber-line::before {
  left: 30%;
}

.cyber-line::after {
  right: 30%;
}

@media (max-width: 600px) {
  .nav-container {
    flex-direction: column;
    gap: 10px;
  }
  
  .nav-links {
    width: 100%;
    justify-content: space-between;
  }
  
  .cyber-ui-element {
    display: none;
  }
}
</style>
