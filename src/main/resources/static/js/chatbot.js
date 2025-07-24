window.addEventListener('load', function () {
  console.log('[Chatbot] Script cargado (window.onload)');

  // Elementos esenciales
  const button = document.getElementById('chatbot-button');
  const windowChat = document.getElementById('chatbot-window');
  const closeBtn = document.getElementById('chatbot-close');
  const sendBtn = document.getElementById('send-button');
  const inputField = document.getElementById('user-message');
  const messageContainer = document.getElementById('chat-messages');

  // Verificación crítica
  if (!button || !windowChat || !closeBtn || !sendBtn || !inputField || !messageContainer) {
    console.error('[Chatbot] Elementos no encontrados en DOM');
    return;
  }

  // Mostrar u ocultar ventana de chat
  const toggleChat = () => {
    windowChat.classList.toggle('show-chat');
    console.log('[Chatbot] Toggle visual');
  };

  // Añadir burbuja de mensaje al chat
  const appendMessage = (text, type = 'bot') => {
    const bubble = document.createElement('div');
    bubble.classList.add('chat-bubble', type);
    bubble.textContent = text;
    messageContainer.appendChild(bubble);
    messageContainer.scrollTop = messageContainer.scrollHeight;

    // Animar el botón al recibir respuesta
    if (type === 'bot') {
      button.classList.add('grow');
      setTimeout(() => button.classList.remove('grow'), 300);
    }
  };

  // Enviar mensaje al backend
  const sendMessage = async () => {
    const msg = inputField.value.trim();
    if (!msg) return;

    appendMessage(msg, 'user'); // Mensaje del usuario
    inputField.value = '';

    try {
      const response = await fetch('/api/chatbot', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message: msg })
      });
      const data = await response.json();
      appendMessage(data.reply || 'No entendí esa parte 😅', 'bot');
    } catch (err) {
      console.error('[Chatbot] Error al enviar:', err);
      appendMessage('Hubo un error al contactar al servidor 🤖', 'bot');
    }
  };

  // Asignar eventos
  button.onclick = toggleChat;
  closeBtn.onclick = toggleChat;
  sendBtn.onclick = sendMessage;
  inputField.addEventListener('keypress', e => {
    if (e.key === 'Enter') sendMessage();
  });

  console.log('[Chatbot] Eventos registrados correctamente');
});