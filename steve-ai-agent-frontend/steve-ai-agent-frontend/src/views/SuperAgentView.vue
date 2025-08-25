<template>
  <div class="container">
    <h1 class="page-title">AI 超级智能体</h1>
    <ChatInterface 
      :chatId="chatId" 
      :apiEndpoint="apiEndpoint"
      :separateMessages="true"
      :chatTitle="chatTitle"
      :aiAvatar="aiAvatar"
    />
    <div class="author-info">
      Powered by Steve.Yz <a href="https://github.com/SteveYz2002" target="_blank" rel="noopener noreferrer">@GitHub</a>
    </div>
  </div>
</template>

<script>
import ChatInterface from '../components/ChatInterface.vue'
import { v4 as uuidv4 } from 'uuid'

export default {
  name: 'SuperAgentView',
  components: {
    ChatInterface
  },
  data() {
    return {
      chatId: '',
      apiEndpoint: '/api/ai/manus/chat',
      chatTitle: 'AI 超级智能体',
      aiAvatar: '/images/super-agent-avatar.svg'
    }
  },
  created() {
    // Generate a unique chatId when the component is created
    this.chatId = uuidv4()
  }
}
</script>

<style scoped>
.author-info {
  text-align: center;
  margin-top: 15px;
  font-size: 0.8rem;
  color: var(--text-light);
  font-family: 'Courier New', monospace;
}

.author-info a {
  color: var(--primary-color);
  text-decoration: none;
  transition: all 0.3s ease;
}

.author-info a:hover {
  text-shadow: 0 0 5px var(--primary-color);
}
</style> 