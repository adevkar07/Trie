# Trie


Input Format : 

1) You can use options to operate on Trie(Add,Delete,Get).
Each option when selected can be used until “End” is typed.

Notes: 
1) While removing if any word contains sub word which was added individually then, I am not removing that word branch. 
Else, if any node does not have any child mappings then that node gets removed recursively.

2) Get function returns sequence number at which given word was added in Trie. That is value with which it got added.

3) We can test both iterator functions and normal functions. For now, I have commented Iterator functions in main method.
