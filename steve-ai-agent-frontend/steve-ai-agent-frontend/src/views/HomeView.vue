<template>
  <div class="cyber-container">
    <div class="matrix-bg"></div>
    
    <div class="glitch-title">
      <span aria-hidden="true">AI 智能助手平台</span>
      AI 智能助手平台
      <span aria-hidden="true">AI 智能助手平台</span>
    </div>
    
    <div class="cyber-subtitle">
      <span class="typing-text">// 解锁 AI 的无限可能</span>
    </div>
    
    <div class="home-grid">
      <div class="cyber-card" @click="navigateTo('love-master')">
        <div class="card-glow"></div>
        <div class="card-content">
          <div class="card-icon love">❤️</div>
          <div class="card-header">
            <div class="card-title">&lt;恋爱补给站 AI /&gt;</div>
            <div class="card-corner"></div>
          </div>
          <p class="card-description">与 恋爱补给站 AI 进行聊天，获得恋爱建议和情感支持</p>
          <div class="cyber-btn">
            <span>></span> 立即体验 <span class="glitch-small">[01]</span>
          </div>
        </div>
      </div>
      
      <div class="cyber-card" @click="navigateTo('super-agent')">
        <div class="card-glow"></div>
        <div class="card-content">
          <div class="card-icon brain">🧠</div>
          <div class="card-header">
            <div class="card-title">&lt;AI 超级智能体/&gt;</div>
            <div class="card-corner"></div>
          </div>
          <p class="card-description">与更强大的 AI 智能体对话，解决复杂问题</p>
          <div class="cyber-btn">
            <span>></span> 立即体验 <span class="glitch-small">[02]</span>
          </div>
        </div>
      </div>
    </div>
    
    <div class="creator-info">
      <div class="creator-tag">CREATOR</div>
      <a href="https://github.com/SteveYz2002" target="_blank" rel="noopener noreferrer" class="creator-link">
        <span class="creator-name">Steve.Yz</span>
        <span class="creator-github">@GitHub</span>
      </a>
    </div>
    
    <div class="tech-decor">
      <div class="circuit-line"></div>
      <div class="circuit-line"></div>
      <div class="circuit-line"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomeView',
  mounted() {
    // 创建数字雨效果
    this.createMatrixEffect();
  },
  methods: {
    navigateTo(route) {
      this.$router.push(`/${route}`)
    },
    createMatrixEffect() {
      const canvas = document.createElement('canvas');
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;
      canvas.classList.add('matrix-canvas');
      document.querySelector('.matrix-bg').appendChild(canvas);

      const ctx = canvas.getContext('2d');
      const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
      const fontSize = 14;
      const columns = Math.floor(canvas.width / fontSize);
      const drops = Array(columns).fill(1);

      function draw() {
        ctx.fillStyle = 'rgba(0, 0, 0, 0.05)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        ctx.fillStyle = '#0f0';
        ctx.font = `${fontSize}px monospace`;

        for (let i = 0; i < drops.length; i++) {
          const text = characters.charAt(Math.floor(Math.random() * characters.length));
          ctx.fillText(text, i * fontSize, drops[i] * fontSize);

          if (drops[i] * fontSize > canvas.height && Math.random() > 0.975) {
            drops[i] = 0;
          }
          drops[i]++;
        }
      }

      setInterval(draw, 33);

      window.addEventListener('resize', () => {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
      });
    }
  }
}
</script>

<style scoped>
.cyber-container {
  position: relative;
  min-height: 100vh;
  background-color: #0a0a14;
  padding: 2rem 0;
  overflow: hidden;
  color: #fff;
}

.matrix-bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
  opacity: 0.3;
}

.matrix-canvas {
  position: absolute;
  top: 0;
  left: 0;
}

.glitch-title {
  position: relative;
  font-size: 3rem;
  font-weight: 700;
  text-align: center;
  color: #fff;
  margin-top: 2rem;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  text-shadow: 
    0 0 5px #00fffc,
    0 0 10px #00fffc,
    0 0 20px #00fffc;
  animation: glitch 2s infinite;
}

.glitch-title span {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
}

.glitch-title span:first-child {
  clip-path: polygon(0 0, 100% 0, 100% 45%, 0 45%);
  transform: translate(-0.04em, -0.03em);
  opacity: 0.8;
  animation: glitch-top 2s infinite linear alternate-reverse;
}

.glitch-title span:last-child {
  clip-path: polygon(0 55%, 100% 55%, 100% 100%, 0 100%);
  transform: translate(0.04em, 0.03em);
  opacity: 0.8;
  animation: glitch-bottom 2s infinite linear alternate-reverse;
}

@keyframes glitch-top {
  0% { transform: translate(-0.04em, -0.03em); }
  20% { transform: translate(0.04em, -0.03em); }
  40% { transform: translate(-0.04em, -0.03em); }
  60% { transform: translate(0.04em, 0.03em); }
  80% { transform: translate(-0.04em, -0.03em); }
  100% { transform: translate(0.04em, -0.03em); }
}

@keyframes glitch-bottom {
  0% { transform: translate(0.04em, 0.03em); }
  20% { transform: translate(-0.04em, 0.03em); }
  40% { transform: translate(0.04em, 0.03em); }
  60% { transform: translate(-0.04em, -0.03em); }
  80% { transform: translate(0.04em, 0.03em); }
  100% { transform: translate(-0.04em, 0.03em); }
}

.cyber-subtitle {
  text-align: center;
  color: #00fffc;
  font-family: monospace;
  font-size: 1.2rem;
  margin-bottom: 3rem;
}

.typing-text {
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  border-right: 0.15em solid #00fffc;
  animation: typing 4s steps(30) 1s 1 normal both, blink 1s steps(1) infinite;
}

@keyframes typing {
  from { width: 0; }
  to { width: 100%; }
}

@keyframes blink {
  50% { border-color: transparent; }
}

.home-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin: 2rem auto;
  max-width: 1200px;
  padding: 0 1rem;
  position: relative;
  z-index: 1;
}

.cyber-card {
  position: relative;
  background: linear-gradient(45deg, #181818, #1f1f2b);
  border: 1px solid #333;
  border-radius: 5px;
  height: 100%;
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
}

.cyber-card::before {
  content: '';
  position: absolute;
  top: -2px;
  left: -2px;
  right: -2px;
  bottom: -2px;
  background: linear-gradient(45deg, #ff00cc, #00fffc, #ff00cc);
  z-index: -1;
  border-radius: 7px;
  animation: border-glow 3s ease infinite alternate;
}

.card-glow {
  position: absolute;
  width: 150%;
  height: 150%;
  top: -25%;
  left: -25%;
  background: radial-gradient(
    circle at center,
    rgba(0, 255, 252, 0.1) 0%,
    transparent 70%
  );
  opacity: 0;
  transition: opacity 0.3s ease;
  pointer-events: none;
}

.cyber-card:hover .card-glow {
  opacity: 1;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% { opacity: 0.2; }
  50% { opacity: 0.4; }
  100% { opacity: 0.2; }
}

.cyber-card:hover {
  transform: translateY(-10px);
  box-shadow: 
    0 0 10px rgba(0, 255, 252, 0.3),
    0 0 30px rgba(0, 255, 252, 0.2);
}

@keyframes border-glow {
  0% { opacity: 0.5; }
  50% { opacity: 0.8; }
  100% { opacity: 0.5; }
}

.card-content {
  padding: 2rem;
  position: relative;
  z-index: 1;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  position: relative;
}

.card-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #fff;
  font-family: 'Courier New', monospace;
  text-shadow: 0 0 5px #00fffc;
}

.card-corner {
  position: absolute;
  right: -2rem;
  top: -2rem;
  width: 3rem;
  height: 3rem;
  border-left: 2px solid #00fffc;
  border-bottom: 2px solid #00fffc;
}

.card-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
  text-align: center;
  background: rgba(0, 0, 0, 0.3);
  width: 70px;
  height: 70px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  margin-left: auto;
  margin-right: auto;
  position: relative;
}

.card-icon::after {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  border: 2px solid transparent;
  animation: rotate 3s linear infinite;
}

.card-icon.love::after {
  border-top: 2px solid #ff00cc;
  border-right: 2px solid transparent;
}

.card-icon.brain::after {
  border-top: 2px solid #00fffc;
  border-right: 2px solid transparent;
}

@keyframes rotate {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.card-description {
  color: #aaa;
  margin-bottom: 2rem;
  line-height: 1.6;
  font-size: 1rem;
}

.cyber-btn {
  display: inline-block;
  background-color: rgba(0, 255, 252, 0.1);
  color: #00fffc;
  padding: 0.8rem 1.5rem;
  text-align: center;
  position: relative;
  overflow: hidden;
  transition: all 0.3s ease;
  border-left: 2px solid #00fffc;
  font-family: 'Courier New', monospace;
  width: 100%;
}

.cyber-btn::before {
  content: '';
  position: absolute;
  left: -2px;
  bottom: 0;
  width: 10px;
  height: 2px;
  background-color: #00fffc;
}

.cyber-btn::after {
  content: '';
  position: absolute;
  right: 0;
  top: -2px;
  width: 10px;
  height: 2px;
  background-color: #00fffc;
}

.cyber-btn:hover {
  background-color: rgba(0, 255, 252, 0.2);
  box-shadow: 0 0 10px rgba(0, 255, 252, 0.5);
  transform: translateY(-2px);
}

.glitch-small {
  position: relative;
  display: inline-block;
  animation: small-glitch 2s infinite;
}

@keyframes small-glitch {
  0% { transform: translateX(0); }
  25% { transform: translateX(2px); }
  50% { transform: translateX(-2px); }
  75% { transform: translateX(1px); }
  100% { transform: translateX(0); }
}

.tech-decor {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: space-between;
  padding: 0 2rem;
}

.circuit-line {
  width: 30%;
  height: 2px;
  background: linear-gradient(to right, transparent, #00fffc, transparent);
  position: relative;
}

.circuit-line::before,
.circuit-line::after {
  content: '';
  position: absolute;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: #00fffc;
  top: -4px;
  box-shadow: 0 0 10px #00fffc;
}

.circuit-line::before {
  left: 30%;
}

.circuit-line::after {
  right: 30%;
}

.creator-info {
  position: relative;
  margin: 60px auto 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  z-index: 2;
}

.creator-tag {
  font-size: 0.7rem;
  letter-spacing: 2px;
  color: var(--text-light);
  background-color: rgba(0, 0, 0, 0.3);
  padding: 3px 8px;
  border-top: 1px solid var(--primary-color);
}

.creator-link {
  display: flex;
  align-items: center;
  gap: 8px;
  text-decoration: none;
  padding: 5px 15px;
  background-color: rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(0, 255, 252, 0.2);
  border-radius: 3px;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.creator-link::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(0, 255, 252, 0.1), transparent);
  transition: left 0.5s ease;
}

.creator-link:hover {
  border-color: var(--primary-color);
  box-shadow: 0 0 10px rgba(0, 255, 252, 0.3);
}

.creator-link:hover::before {
  left: 100%;
}

.creator-name {
  color: var(--primary-color);
  font-weight: bold;
  font-family: 'Courier New', monospace;
  text-shadow: 0 0 5px rgba(0, 255, 252, 0.5);
}

.creator-github {
  color: var(--text-light);
  font-size: 0.8rem;
  opacity: 0.7;
}

@media (max-width: 768px) {
  .glitch-title {
    font-size: 2rem;
  }
  
  .cyber-subtitle {
    font-size: 1rem;
  }
  
  .home-grid {
    gap: 1.5rem;
  }
  
  .card-icon {
    font-size: 2.5rem;
    width: 60px;
    height: 60px;
  }
  
  .card-title {
    font-size: 1.2rem;
  }
}

@media (max-width: 480px) {
  .glitch-title {
    font-size: 1.5rem;
  }
  
  .cyber-subtitle {
    font-size: 0.9rem;
    margin-bottom: 2rem;
  }
  
  .card-content {
    padding: 1.5rem;
  }
  
  .card-icon {
    width: 50px;
    height: 50px;
    font-size: 2rem;
  }
  
  .card-title {
    font-size: 1rem;
  }
}
</style> 