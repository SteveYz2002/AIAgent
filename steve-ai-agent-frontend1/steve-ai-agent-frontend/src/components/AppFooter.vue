<template>
  <footer class="cyber-footer">
    <div class="footer-content">
      <div class="footer-info">
        <span class="copyright">© {{ currentYear }} </span>
        <span class="author">Made by <a href="https://github.com/SteveYz2002" target="_blank" rel="noopener noreferrer">Steve.Yz</a></span>
      </div>
      <div class="footer-decoration">
        <span class="footer-bracket">&lt;/&gt;</span>
        <div class="circuit-line"></div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'AppFooter',
  computed: {
    currentYear() {
      return new Date().getFullYear();
    }
  }
}
</script>

<style scoped>
.cyber-footer {
  margin-top: auto;
  padding: 15px 0;
  background-color: var(--bg-dark);
  border-top: 1px solid var(--primary-color);
  color: var(--text-light);
  font-family: 'Courier New', monospace;
  position: relative;
  overflow: hidden;
}

.footer-content {
  max-width: var(--container-width);
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.footer-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.author a {
  color: var(--primary-color);
  text-decoration: none;
  position: relative;
  transition: all 0.3s ease;
}

.author a::before {
  content: '';
  position: absolute;
  bottom: -2px;
  left: 0;
  width: 100%;
  height: 1px;
  background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
  transform: scaleX(0);
  transform-origin: left;
  transition: transform 0.3s ease;
}

.author a:hover {
  text-shadow: 0 0 5px var(--primary-color);
}

.author a:hover::before {
  transform: scaleX(1);
}

.footer-decoration {
  display: flex;
  align-items: center;
  gap: 10px;
}

.footer-bracket {
  color: var(--primary-color);
  font-weight: bold;
  opacity: 0.7;
}

.circuit-line {
  width: 50px;
  height: 2px;
  background: linear-gradient(90deg, var(--primary-color), transparent);
  position: relative;
}

.circuit-line::before {
  content: '';
  position: absolute;
  left: 10px;
  top: -3px;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: var(--primary-color);
  box-shadow: 0 0 5px var(--primary-color);
  opacity: 0.7;
  animation: pulse 2s infinite alternate;
}

@media (max-width: 600px) {
  .footer-content {
    flex-direction: column;
    gap: 10px;
    text-align: center;
  }
}
</style> 