Сетевое взаимодействие клиент-сервер в Java. Основные классы и методы для работы с сетью.

В модели Клиент - Сервер можно выделить три класса:
Сервер 
Клиент
Обработчик клиентов

Сокет
Socket(String имя_хоста, int порт) throws UnknownHostException, IOException
Socket(InetAddress IP-адрес, int порт) throws UnknownHostException

Методы Socket:
InetAddress getInetAddress()

int getPort()

int getLocalPort()

boolean isConnected()

void connect(SocketAddress адрес)

boolean isClosed()

boolean isBound()

ServerSocket
ServerSocket()throws IOExceptionServerSocket(int порт)throws IOExceptionServerSocket(int порт,int максимум_подключений)throws IOExceptionServerSocket(int порт,int максимум_подключений, InetAddress локальный_адрес)throws IOException

Доступ к потоку
InputStream getInputStream()
OutputStream getOutputStream()
BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));