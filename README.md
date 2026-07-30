# 🚗 Inspection Checklist

Aplicação Android nativa para **gestão de inspeções técnicas de veículos - ITAC**, desenvolvida em Kotlin. Permite registar veículos, realizar inspeções periódicas de acordo com o tipo de viatura (Ligeiro Particular, Ligeiro Transporte Público e Pesado), gerir reinspeções de veículos reprovados e acompanhar tudo através de um dashboard com estatísticas.

O **Inspection Checklist** foi criado para digitalizar e simplificar o processo de inspeção técnica automóvel, substituindo checklists em papel por um fluxo de trabalho estruturado e persistente no dispositivo. A aplicação organiza os veículos por tipo, guia o inspetor por um checklist específico consoante a categoria da viatura, calcula automaticamente o resultado (Aprovado/Reprovado) e, em caso de reprovação, encaminha o veículo para reinspeção — mantendo um histórico completo de todas as inspeções realizadas.

## Funcionalidades

- **Gestão de Veículos**
  - Registo de novos veículos (matrícula por ilha, marca, modelo, proprietário, ano, tipo)
  - Validação de matrícula por formato específico de cada ilha
  - Edição e remoção de veículos (bloqueada se o veículo já tiver inspeções associadas)
  - Listagem com filtro por tipo de veículo

- **Inspeção Periódica**
  - Checklists distintos por categoria de veículo:
    - Ligeiro Particular
    - Ligeiro Transporte Público
    - Pesado
  - Validação obrigatória de todos os campos antes de submeter
  - Cálculo automático do resultado (Aprovado/Reprovado)
  - Registo de observações e categorias reprovadas

- **Reinspeção**
  - Lista de veículos reprovados aguardando reinspeção
  - Tela dinâmica que exibe apenas as categorias previamente reprovadas
  - Atualização do estado (aprovado ou mantém-se em reinspeção com as falhas restantes)

- **Histórico de Inspeções**
  - Lista de todas as inspeções realizadas
  - Filtros por tipo (Periódico / Reinspeção) e por resultado (Aprovado / Reprovado)

- **Dashboard**
  - Total de veículos registados e distribuição por tipo
  - Total de inspeções realizadas
  - Percentagem de aprovação e reprovação
  - Inspeções realizadas no ano corrente
  - Total de inspeções periódicas e reinspeções

- **Persistência de Dados**
  - Armazenamento local em ficheiros JSON

## 🛠️ Tech Stack

- **Linguagem:** Kotlin
- **Plataforma:** Android Studio
- **Persistência:** Armazenamento local em JSON
- **UI:** Views XML

## 📸 Screenshots

<table>
  <tr>
    <td><img width="200" src="https://github.com/user-attachments/assets/e63d59a0-1312-454d-984b-9717049e9876" /></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/db029706-597b-4b82-ac9e-569f0eada8fc" /></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/34b54473-7700-4744-a5f9-4f38d304603d" /></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/dab8a13e-5453-464f-a310-7ff355a752e0" /></td>
  </tr>
  <tr>
    <td><img width="200" src="https://github.com/user-attachments/assets/8278bda7-e0c4-4d21-b56c-8a359c12fe4d" /></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/76a22816-2f00-4aa6-ad39-fdeb9fa93ae8" /></td>
    <td><img width="200" src="https://github.com/user-attachments/assets/6089fac6-134b-4536-b93e-87fb8f3eff28" /></td>
  </tr>
</table>

## ⚙️ Como Funciona

1. **Ecrã principal (Menu):** ponto de partida com acesso a Novo Veículo, Inspeção, Lista de Veículos e Dashboard.

2. **Registo de veículo:** o utilizador preenche os dados do veículo (ilha + matrícula, marca, modelo, proprietário, ano e tipo). Após validação, o veículo é adicionado à lista geral de veículos e automaticamente à lista de veículos pendentes de inspeção periódica.

3. **Inspeção periódica:** ao selecionar um veículo pendente, a aplicação abre automaticamente o checklist correspondente ao seu tipo (Ligeiro Particular, Ligeiro Transporte Público ou Pesado). Cada item do checklist é avaliado como "Conforme" ou "Não Conforme". Quando todos os campos estão preenchidos:
   - Se todos os itens estiverem conformes → resultado **Aprovado**.
   - Se algum item estiver não conforme → resultado **Reprovado**, e o veículo é automaticamente adicionado à lista de reinspeções.
   - Em ambos os casos, a inspeção é guardada no histórico geral e o veículo sai da lista de inspeção periódica.

4. **Reinspeção:** o veículo reprovado aparece na lista de reinspeções, mostrando apenas as categorias que falharam anteriormente. O utilizador reavalia apenas esses itens:
   - Se todos passarem a "Conforme" → o veículo é **Aprovado** e sai da lista de reinspeção.
   - Se ainda houver itens "Não Conforme" → o veículo permanece em reinspeção, agora apenas com as falhas restantes.

5. **Histórico e filtros:** todas as inspeções (periódicas e reinspeções) ficam disponíveis numa lista única, filtrável por tipo e por resultado.

6. **Dashboard:** apresenta um resumo estatístico em tempo real com os totais e percentagens calculados a partir dos dados guardados (veículos e inspeções).

7. **Persistência:** todos os dados (veículos, inspeções e reinspeções) são guardados localmente em ficheiros `.json` no armazenamento interno da aplicação, sendo recarregados automaticamente ao iniciar.
