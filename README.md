##### Android Challenge #####

### 1. Criar tela de Login
- [x] Criar Layout
- [x] Validar campos de login e  senha
- [x] Usar DataBinding
- [x] Navegar para tela de Registro

### 2. Criar tela de Registrar Usuario
- [X] Criar layout
- [X] Validar campos
- [X] Aplicar mascaras CPF e Telefone
- [X] Salvar dados no SharedPreference
- [X] Mostrar toast de sucesso
    
### 3. Implementar Login
- [X] Verificar se imput bate com algum registro do Banco
- [X] Caso sucesso ir para Home
- [X] Navegar para Home fazendo Slide In from Left
- [X] Evitar voltar para Login quando estiver na Home. COM NAVIGATION
- [X] Caso falha mostrar Dialog de qual foi erro Ex: Usuario inexistente ou Dados incorretos
- [X] Mostrar na tela de Home o Bem vindo <Nome do Usuario>. Usando SAFE_ARGS
    
### 4. Implementar um RecycleView
- [x] Importar lista de usuarios cadastrados do BD
- [x] Mostrar lista de usuarios cadastrados em forma de lista usando RecycleView
- [x] Cada item da lista deve ter:
* Imagem aleatoria entre os itens (ou seja nao pode ser todas iguais mas pode repetir)
* Nome Sobrenome
* Telefone
* Email
* CPF

- [x] Botão de voltar e no recycleView tb
- [x] Alterar Usuario para que ele tenha endereco de uma imagem como foto
- [x] Alterar lista recebida da API adicionando 1 URL aleatoria de 5 URls que voce vai criar
- [x] Itens com layout de CardView https://medium.com/android-beginners/cardview-android-example-beginners-dde933585261
- [x] Usar Glide ou Picasso para carregar imagem da internet. Escolher uma imagem aleatoria de 5.

### 5. HomeActivity
- [x] Titulo na toolbar
- [x] Menu na toolbar
- [x] Extrair texto harded code. Implementando 
    https://developer.android.com/guide/topics/resources/string-resource.html#formatting-strings
    
### 6. GIT
- [x] Estudar curso GIT
- [x] Git add 
- [x] Git commit
- [x] Git Push
- [x] Git Pull
- [x] Git Merge
- [x] Git Fetch - Atualiza sua maquina
- [x] Pull Request
   
### 7. Htttp Connection
    * https://jsonplaceholder.typicode.com/
- [x] Consumir um conteudo de um serviço
- [x] Mostrar o conteudo na tela

### 8. Adicionar tela de detalhes do usuario
- [x] Ao clicar em um usuário da lista abrir uma nova tela, passando o usuario por safeArgs (Como passar dados por safeArgs android) na tela de detalhe do pedido
- [x] Collapsing toolbar, ao deslizar pra baixo ele expande o toolbar, pra cima colapsa https://medium.com/martinomburajr/android-design-collapsing-toolbar-scrollflags-e1d8a05dcb02
- [x] Dentro do toolbar ficará a foto no background
- [x] Sobrepondo a imagem, no bottom do toolbar ficara o nome completo
- [x] No corpo da tela teremos uma CardView https://n1lesh.medium.com/android-cardview-101-everything-you-should-know-5bbf1c873f5a
- [x] Voltar na toolbar

### 9. Adicionar um loading enquanto estiver fazendo o fetch dos dados da API. Para simular
- [x] Mostrar um pino no local da lat long do objeto clicado

### 10. Adicionar botao de setar data de nascimento dentro do detalhes do Usuario.
- [x] Obter a DATA escolhida e mostrar no card
- [x] Obter a HORA escolhida e mostrar no card

### 11. Salvar os usuário dentro do banco de dados
- [x] Obter os usuários da API
- [ ] Salvar os usuários da API dentro do Firebase quando fizer SignUp
- [ ] Alterar codigo para obter a lista de usuario do Firebase

### 12. Adicionar login por rede social
- [x] Na tela de login deve ter a opção de entrar com Facebook
- [x] Na tela de login deve ter a opção de entrar com Google
- [x] Na tela de login deve ter a opção de entrar com Email
- [x] Caso botão clicado seja e-mail, ele direciona para um nova tela para inserir email e senha
    
<img width="250" alt="Tela de login" src="https://user-images.githubusercontent.com/12714219/126506469-07e6c1e5-456c-4459-bc44-9276a9b9151f.png">

    
### 13. Melhorar o layout
- Geral
- [x] Trocar cor das setas de retorno para a cor do texto da toolbar ("Branco")
- [x] Pesquisar todas telas com toolbar (Usar thema do app)

- Login Home
- [x] Sem toolbar
- [x] O logo deve iniciar a 20% do tamanho da tela
- [x] Aumentar espaco dos icones dos botoes
- [x] Texto SignUp deve enviar para tela de SigUp ao clicar 

- Login Form
- [x] Titulo da tela
- [x] O hint dos campos estao muito pra dentro
- [x] Testar colcar campos em box
- [x] Toolbar esta deslizando junto com os campos

- Signup
- [x] Titulo da tela ir para toolbar removendo de dentro do fragment
- [x] Toolbar esta deslizando junto com os campos


- [x] Testar colcar campos em box

- RecycleView
- [x] Titulo deve representar melhor o que a tela faz
https://docs.microsoft.com/pt-br/xamarin/android/user-interface/controls/card-view-images/01-cardview-example.png
- [x] Imagem no topo
- [x] Nome no bottom da imagem com fundo preto e transparencia de 80% (opacidade)
- [x] Remover os ":" e deixar label em negrito e valor em normal
- [x] Em baixo os outros campos em cor preta fundo branco

- Detalhes do item clicado
- [x] Manter collapse toolbar
- [x] Remover fundo do card
- [x] Titulo na base da imassim assim como feito
- [ ] A imagem deve animar saindo da do item clicado e voar para tela de detalhe do pedido e expandir
https://www.youtube.com/watch?v=KuV8Y9-T-oA
- [x] A tela de ser a mesma configuracao do card, soh que nao sera um card, da lista como na foto
https://docs.microsoft.com/pt-br/xamarin/android/user-interface/controls/card-view-images/01-cardview-example.png
- [x] Mostrar todos os dados que temos, assim como ja esta implementado
- [x] Espacmento do texto nas laterais deve ser de 16/18 dp, mas somente no texto a imagem fica borda a borda
- [x] No bottom da tela teremos os dois botoes(Texto) Date e Map
- [ ] Trocar icone logout por camera
- [ ] Ao clicar no icone da camera na toolbar devemos dar opcao, atraves de um dialog, de tirar foto ou carregar imagem
- [ ] Colocar botao de adicionar imagem do celular
- [x] Aumentar tamanho do collpse toolbar para 40% da tela (veja se da pra fazer isso)
- [ ] Caso o usuario ja tenha escolhido a data de nascimento, desabilitar o botão, porém continua mostrar (cinza claro)
- [ ] Atualizar o objeto Usuario com os dados de nascimento pegos no Date Picker 
- [x] adicionar no item texto caixa alta SHARE (Abre apps com plain-text)
https://developer.android.com/training/sharing/send

- Map
- [x] Adicionar toolbar
- [x] Nome da tela na toolbar Map
- [ ] Adicionar movimentacao de camera
- [ ] Ao clicar no map, checar permissao
- [ ] Ao abrir o map dar zoom na localizacao atual do usuario
- [ ] Adicionar um botao flutuante "OK"
- [ ] Ao clicar clicar e segurar no marcador ele deixa alterar a localizacao
- [ ] Ao clicar em "OK" vamos salva no usuario o Endereco do usuario com todos os dados que temos atualmente

- Bugs and Fixes
- [ ] Login diminuir tamanho de logo
- [ ] Aumentar margin da imagem dentro dos botoes
- [ ] Nivelar tamanho dos botoes
- [ ] Nivelar margem entre botoes
- [ ] Remover toolbar da tela de login
- [ ] Remover toolbar duplicada na tela de registrar
- [ ] Fazer toolbar aparecer em todas as telas menos na de login
- [ ] Remover menu logout de todas as telas que nao estejam logadas
- [ ] Ao clicar no texto do bottom da tela de login, SIGN UP, ir para tela de registrar
- [ ] Quando a toolbar aparecer o titulo deve ser a descricao da tela. Ex: registrar, Login com Email, Lista de Usuarios, Detalhe do usuario, etc
- [ ] Quando houver toolbar a seta de voltar deve estar branca
- [ ] Na lista da Home deve mostrar lista de usuarios cadastrados do Firebase e nao mais na API
- [ ] Ao ir para Tela de lista de usuario, ele nao deve mostrar botao voltar
- [ ] Ao clicar em voltar com botao do dispositivo ele minimiza o app ao inves de voltar para Home.



### Opcoes de estudo
#Basisco
* Selectors (Botao com 2 cores, ativo desativo)
* Banco de dados
* Abrir outros apps (email)
    
#Intermediario
* Arquiteturas de projeto 
    MVVM - Model View ViewModel **
    MVP - Model View Presenter
    MVI - Model View Interface
    Viper
* Multithread
* Coroutines
* SOLID
* Clean Code    
* Fazer um recycleView com tipos de View diferentes. Usando `getItemViewType` 
    Artigo top sobre assunto https://betterprogramming.pub/android-recyclerview-with-kotlin-sealed-classes-6d2985aac3e5
