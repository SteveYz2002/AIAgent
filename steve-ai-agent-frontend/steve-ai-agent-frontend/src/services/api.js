import axios from 'axios'

// Create axios instance with base URL
const apiClient = axios.create({
  baseURL: 'http://localhost:8123/api',
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
})

export default {
  // Love Master API
  getLoveMasterChat(message, chatId) {
    return apiClient.get('/ai/love_app/chat/sse', {
      params: {
        message,
        chatId
      }
    })
  },
  
  // Super Agent API
  getSuperAgentChat(message) {
    return apiClient.get('/ai/manus/chat', {
      params: {
        message
      }
    })
  }
} 