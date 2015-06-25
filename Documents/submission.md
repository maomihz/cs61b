## School's Submission System Explained

__For cs61bl students__

These days I heard a lot of people around asking about problems about the submission process and ssh. Those are all unix stuff, but most people are not used to Unix, so there may be wierd things happen like why I cannot copy files to the lab computer or why I submit a long list of files. In this post, I will try to explain all the concepts to help ease this summer's learning. I will not have illustrations but feel free to ask me any questions. BTW, I'm a student in Alice's section. 

__Some of the topics explained may *NOT* apply to a Microsoft Windows computer! (See Mirosoft Windows below)__

### Introduction to lab's computer

Lab's computer is very different from the computers we see in everyday life. There is a central server that is in charge of all the computing work and resource management. All the computers in the lab have actually no computing power and are all connected to the central server. That's why Ubuntu Linux is used, because one of the features of Linux is multiuser. 

Everyone have an account to access the central server. This is to ensure seperation of different user's files without installing different softwares on thousands of different computers. Even if you use different computers everyday, all your files are not deleted. (But keep in mind that your account will be deleted alongside your files after the course ends. )

The central server is open to the Internet, so if you have an account, you can access your lab computer anywhere in the world without entering the lab! (With `ssh` as explained below)

### Submit with my own computer?

`submit` is a program or a script installed on lab computers to submit your work. This is a command line software, so you need to open a terminal on a lab computer to access it. But the software is certainly __NOT__ installed on your own computer! So all you need to do is copy the files to the lab computer and submit. 

So can we use an USB disk to copy the files? Of course you can!(You will find no place to plug in your USB disk though.) But there is a smart way to this: we use `scp`, as explained below, to remotely copy files __over the network__.

### SSH to your machine

-

![Creative Commons](https://i.creativecommons.org/l/by/4.0/88x31.png)
