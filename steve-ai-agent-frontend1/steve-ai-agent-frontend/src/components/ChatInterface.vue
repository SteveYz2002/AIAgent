<template>
  <div class="chat-container">
    <div class="chat-header">
      <div class="chat-title">{{ chatTitle }}</div>
      <div class="chat-id">ID: {{ chatId.substring(0, 8) }}</div>
    </div>
    
    <div class="chat-messages" ref="messagesContainer">
      <div 
        v-for="(message, index) in messages" 
        :key="index" 
        :class="['message-row', message.isUser ? 'user-row' : 'ai-row']"
      >
        <div class="avatar">
          <img v-if="!message.isUser" :src="aiAvatar" alt="AI" class="avatar-img">
          <div v-else class="user-avatar">用户</div>
        </div>
        <div class="message-bubble">
          <div class="message-content">
            {{ message.text }}
            <span v-if="isLoading && index === messages.length - 1 && !message.isUser" class="loading-dots">
              <span class="dot"></span>
              <span class="dot"></span>
              <span class="dot"></span>
            </span>
          </div>
          <div class="message-time">{{ message.time }}</div>
        </div>
      </div>
    </div>
    
    <div class="chat-input">
      <input 
        type="text" 
        v-model="inputMessage" 
        placeholder="输入消息..." 
        @keyup.enter="sendMessage"
        :disabled="isLoading"
      />
      <button @click="sendMessage" :disabled="isLoading || !inputMessage.trim()">
        <span v-if="isLoading">发送中...</span>
        <span v-else>发送</span>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChatInterface',
  props: {
    chatId: {
      type: String,
      required: true
    },
    apiEndpoint: {
      type: String,
      required: true
    },
    separateMessages: {
      type: Boolean,
      default: false
    },
    chatTitle: {
      type: String,
      default: 'AI 助手'
    },
    aiAvatar: {
      type: String,
      default: '/default-avatar.png'
    }
  },
  data() {
    return {
      messages: [],
      inputMessage: '',
      eventSource: null,
      isLoading: false
    }
  },
  methods: {
    formatTime() {
      const now = new Date();
      return `${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`;
    },
    sendMessage() {
      if (!this.inputMessage.trim() || this.isLoading) return;
      
      this.isLoading = true;
      
      // Add user message
      this.messages.push({
        text: this.inputMessage,
        isUser: true,
        time: this.formatTime()
      });
      
      const message = this.inputMessage;
      this.inputMessage = '';
      
      // Start SSE connection
      this.connectToSSE(message);
      
      // Scroll to bottom after message is sent
      this.$nextTick(() => {
        this.scrollToBottom();
      });
    },
    connectToSSE(message) {
      // Close existing connection if any
      if (this.eventSource) {
        this.eventSource.close();
      }
      
      // Create URL with query parameters
      let url = new URL(this.apiEndpoint, 'http://localhost:8123');
      url.searchParams.append('message', message);
      
      if (this.chatId) {
        url.searchParams.append('chatId', this.chatId);
      }
      
      // Create SSE connection
      this.eventSource = new EventSource(url);
      
      // For normal mode (accumulate responses)
      if (!this.separateMessages) {
        // Add a placeholder for AI response
        const responseIndex = this.messages.length;
        this.messages.push({
          text: '',
          isUser: false,
          time: this.formatTime()
        });
        
        // Listen for messages
        this.eventSource.onmessage = (event) => {
          if (event.data) {
            // Update the AI message with received content
            this.messages[responseIndex].text += event.data;
            
            // Scroll to bottom as new content arrives
            this.$nextTick(() => {
              this.scrollToBottom();
            });
          }
        };
      } else {
        // For separate message mode (each event is a new message)
        this.eventSource.onmessage = (event) => {
          if (event.data) {
            // Create a new message for each response
            this.messages.push({
              text: event.data,
              isUser: false,
              time: this.formatTime()
            });
            
            // Scroll to bottom as new content arrives
            this.$nextTick(() => {
              this.scrollToBottom();
            });
          }
        };
      }
      
      // Handle completion
      this.eventSource.addEventListener('complete', () => {
        this.isLoading = false;
        this.eventSource.close();
        this.eventSource = null;
      });
      
      // Handle error
      this.eventSource.onerror = () => {
        this.isLoading = false;
        this.eventSource.close();
        this.eventSource = null;
      };
    },
    scrollToBottom() {
      const container = this.$refs.messagesContainer;
      if (container) {
        container.scrollTop = container.scrollHeight;
      }
    }
  },
  mounted() {
    // Optionally send an initial greeting message
    this.messages.push({
      text: '你好！我是AI助手，有什么可以帮助你的吗？',
      isUser: false,
      time: this.formatTime()
    });
  }
};
</script>

<style scoped>
.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: var(--spacing-sm) var(--spacing-md);
  border-bottom: 1px solid var(--border-color);
}

.chat-title {
  font-weight: bold;
  color: var(--primary-color);
}

.chat-id {
  font-size: 0.8rem;
  color: var(--text-light);
}

.message-row {
  display: flex;
  margin-bottom: var(--spacing-sm);
  align-items: flex-start;
}

.avatar {
  width: 40px;
  height: 40px;
  margin-right: var(--spacing-sm);
  flex-shrink: 0;
}

.avatar-img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
}

.user-avatar {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #e3e3e3;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.8rem;
  color: var(--text-color);
}

.message-bubble {
  background-color: var(--ai-msg-bg);
  padding: var(--spacing-sm) var(--spacing-md);
  border-radius: 15px;
  max-width: calc(100% - 60px);
  word-break: break-word;
}

.ai-row .message-bubble {
  background-color: var(--ai-msg-bg);
}

.user-row .message-bubble {
  background-color: var(--user-msg-bg);
}

.message-content {
  white-space: pre-wrap;
}

.message-time {
  font-size: 0.7rem;
  color: var(--text-light);
  text-align: right;
  margin-top: 5px;
}

.loading-message {
  display: flex;
  align-items: center;
  padding: var(--spacing-sm);
  min-height: 36px;
}

.loading-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: var(--text-light);
  margin: 0 3px;
  opacity: 0.6;
  animation: pulse 1.5s infinite ease-in-out;
}

.loading-dot:nth-child(2) {
  animation-delay: 0.2s;
}

.loading-dot:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.6;
  }
  50% {
    transform: scale(1.2);
    opacity: 1;
  }
}

.loading-dots {
  display: inline-flex;
  align-items: center;
  margin-left: 4px;
}

.dot {
  width: 4px;
  height: 4px;
  border-radius: 50%;
  background-color: var(--primary-color);
  margin: 0 2px;
  animation: dot-pulse 1.5s infinite ease-in-out;
}

.dot:nth-child(2) {
  animation-delay: 0.2s;
}

.dot:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes dot-pulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.6;
  }
  50% {
    transform: scale(1.5);
    opacity: 1;
  }
}

@media (max-width: 480px) {
  .avatar {
    width: 30px;
    height: 30px;
  }
  
  .message-bubble {
    max-width: calc(100% - 40px);
  }
}
</style> 