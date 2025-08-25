<template>
  <div class="cyber-logo">
    <div class="logo-icon">
      <svg width="32" height="32" viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg">
        <defs>
          <linearGradient id="logoGradient" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" stop-color="#00fffc"/>
            <stop offset="100%" stop-color="#ff00cc"/>
          </linearGradient>
        </defs>
        <rect width="32" height="32" rx="3" fill="rgba(0,0,0,0.3)"/>
        <path d="M8 12 L16 8 L24 12 L24 20 L16 24 L8 20 Z" fill="none" stroke="url(#logoGradient)" stroke-width="1.5"/>
        <circle cx="16" cy="16" r="4" fill="none" stroke="url(#logoGradient)" stroke-width="1"/>
        <path d="M10 10 L22 22" stroke="#00fffc" stroke-width="1" opacity="0.8"/>
        <path d="M10 22 L22 10" stroke="#ff00cc" stroke-width="1" opacity="0.8"/>
      </svg>
    </div>
    <div class="logo-text">
      <span class="glitch-text" data-text="AI智能助手">&lt;AI智能助手/&gt;</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AppLogo'
}
</script>

<style scoped>
.cyber-logo {
  display: flex;
  align-items: center;
  gap: 10px;
  position: relative;
}

.logo-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  animation: pulse 4s infinite alternate;
}

.logo-text {
  font-weight: bold;
  font-size: 1.2rem;
  color: var(--primary-color);
  text-shadow: 0 0 5px rgba(0, 255, 252, 0.5);
  font-family: 'Courier New', monospace;
  position: relative;
}

.glitch-text {
  position: relative;
  animation: glitch-skew 1s infinite linear alternate-reverse;
}

.glitch-text::before,
.glitch-text::after {
  content: attr(data-text);
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.glitch-text::before {
  left: 2px;
  text-shadow: -2px 0 #ff00cc;
  clip: rect(44px, 450px, 56px, 0);
  animation: glitch-anim 5s infinite linear alternate-reverse;
}

.glitch-text::after {
  left: -2px;
  text-shadow: -2px 0 #00fffc;
  clip: rect(44px, 450px, 56px, 0);
  animation: glitch-anim-2 5s infinite linear alternate-reverse;
}

@keyframes glitch-anim {
  0% {
    clip: rect(4px, 9999px, 94px, 0);
    transform: skew(0.4deg);
  }
  5% {
    clip: rect(68px, 9999px, 63px, 0);
    transform: skew(0.89deg);
  }
  10% {
    clip: rect(23px, 9999px, 91px, 0);
    transform: skew(0.75deg);
  }
  15% {
    clip: rect(66px, 9999px, 3px, 0);
    transform: skew(0.18deg);
  }
  20% {
    clip: rect(11px, 9999px, 71px, 0);
    transform: skew(0.65deg);
  }
  25% {
    clip: rect(1px, 9999px, 58px, 0);
    transform: skew(0.25deg);
  }
  30% {
    clip: rect(89px, 9999px, 37px, 0);
    transform: skew(0.39deg);
  }
  35% {
    clip: rect(9px, 9999px, 80px, 0);
    transform: skew(0.63deg);
  }
  40% {
    clip: rect(45px, 9999px, 19px, 0);
    transform: skew(0.26deg);
  }
  45% {
    clip: rect(39px, 9999px, 31px, 0);
    transform: skew(0.11deg);
  }
  50% {
    clip: rect(18px, 9999px, 39px, 0);
    transform: skew(0.5deg);
  }
  55% {
    clip: rect(25px, 9999px, 93px, 0);
    transform: skew(0.91deg);
  }
  60% {
    clip: rect(63px, 9999px, 59px, 0);
    transform: skew(0.03deg);
  }
  65% {
    clip: rect(69px, 9999px, 5px, 0);
    transform: skew(0.6deg);
  }
  70% {
    clip: rect(9px, 9999px, 100px, 0);
    transform: skew(0.32deg);
  }
  75% {
    clip: rect(51px, 9999px, 13px, 0);
    transform: skew(0.97deg);
  }
  80% {
    clip: rect(83px, 9999px, 10px, 0);
    transform: skew(0.34deg);
  }
  85% {
    clip: rect(26px, 9999px, 87px, 0);
    transform: skew(0.14deg);
  }
  90% {
    clip: rect(19px, 9999px, 55px, 0);
    transform: skew(0.43deg);
  }
  95% {
    clip: rect(56px, 9999px, 95px, 0);
    transform: skew(0.53deg);
  }
  100% {
    clip: rect(24px, 9999px, 48px, 0);
    transform: skew(0.5deg);
  }
}

@keyframes glitch-anim-2 {
  0% {
    clip: rect(67px, 9999px, 11px, 0);
    transform: skew(0.09deg);
  }
  5% {
    clip: rect(49px, 9999px, 67px, 0);
    transform: skew(0.47deg);
  }
  10% {
    clip: rect(88px, 9999px, 69px, 0);
    transform: skew(0.69deg);
  }
  15% {
    clip: rect(91px, 9999px, 71px, 0);
    transform: skew(0.57deg);
  }
  20% {
    clip: rect(80px, 9999px, 61px, 0);
    transform: skew(0.85deg);
  }
  25% {
    clip: rect(5px, 9999px, 37px, 0);
    transform: skew(0.47deg);
  }
  30% {
    clip: rect(71px, 9999px, 60px, 0);
    transform: skew(0.75deg);
  }
  35% {
    clip: rect(41px, 9999px, 18px, 0);
    transform: skew(0.43deg);
  }
  40% {
    clip: rect(8px, 9999px, 98px, 0);
    transform: skew(0.51deg);
  }
  45% {
    clip: rect(96px, 9999px, 96px, 0);
    transform: skew(0.54deg);
  }
  50% {
    clip: rect(84px, 9999px, 59px, 0);
    transform: skew(0.82deg);
  }
  55% {
    clip: rect(6px, 9999px, 97px, 0);
    transform: skew(0.57deg);
  }
  60% {
    clip: rect(66px, 9999px, 7px, 0);
    transform: skew(0.57deg);
  }
  65% {
    clip: rect(17px, 9999px, 53px, 0);
    transform: skew(0.28deg);
  }
  70% {
    clip: rect(28px, 9999px, 82px, 0);
    transform: skew(0.68deg);
  }
  75% {
    clip: rect(86px, 9999px, 37px, 0);
    transform: skew(0.03deg);
  }
  80% {
    clip: rect(39px, 9999px, 74px, 0);
    transform: skew(0.15deg);
  }
  85% {
    clip: rect(63px, 9999px, 99px, 0);
    transform: skew(0.84deg);
  }
  90% {
    clip: rect(90px, 9999px, 51px, 0);
    transform: skew(0.67deg);
  }
  95% {
    clip: rect(55px, 9999px, 10px, 0);
    transform: skew(0.19deg);
  }
  100% {
    clip: rect(84px, 9999px, 42px, 0);
    transform: skew(0.24deg);
  }
}

@keyframes glitch-skew {
  0% {
    transform: skew(-0.5deg);
  }
  10% {
    transform: skew(0.8deg);
  }
  20% {
    transform: skew(-0.5deg);
  }
  30% {
    transform: skew(0.2deg);
  }
  40% {
    transform: skew(-0.4deg);
  }
  50% {
    transform: skew(0.5deg);
  }
  60% {
    transform: skew(-0.9deg);
  }
  70% {
    transform: skew(0.6deg);
  }
  80% {
    transform: skew(0.3deg);
  }
  90% {
    transform: skew(-0.5deg);
  }
  100% {
    transform: skew(-0.2deg);
  }
}
</style> 