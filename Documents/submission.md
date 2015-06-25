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

`ssh` is a program installed on Unix and Mac computers by default. SSH means 'secure shell', and shell is a program that interpret the commands you give to the computer and run certain programs. (Like Siri, who interpret what you say and do certain task). Using `ssh`, you can __remotely__ login to a computer and open a shell program, so you have all the control power for a computer over the network. But what's different from a remote desktop is SSH does not allow graphical windows to open across the network! So you need to type commands to operate the remote computer. ~~(Of course X11 tunnel can do this)~~ Don't be afraid of typing commands! This is the coolest thing a computer science student can do! 

SSH is secure because all the network traffic is encrypted. To open a SSH session, open Terminal in your computer and type `ssh -V` to see if everything works. The output in my computer is `OpenSSH_6.2p2, OSSLShim 0.9.8r 8 Dec 2011`

The basic syntax of `ssh` command is `ssh user@address`. For example, you want to open an ssh session to `192.168.1.1` and login with user account `maomi`, you type `ssh maomi@192.168.1.1` and hit enter. It will prompt you password. As you type, the characters __WON'T__' show up on the screen and when you finished typing, just press enter. 

Now you will get a message like this: 
```
Last login: Sun Jun 21 21:50:25 2015 from 75-147-140-162-sfba.hfc.comcastbusiness.net
[maomi@maomihz.com ~]# 
```

You have successfully logined remotely to your computer!!! For lab's computer, there will be a bunch of messages show up, giving you some warnings like process running for 24 hours will be killed and infomations like disk quota. Then there is a shell __prompt__ telling you to enter commands. Don't mix up your local terminal and remote shell! Once you sshed to a remote server, you should type `exit` logout to return to your own computer. 

-

![Creative Commons](https://i.creativecommons.org/l/by/4.0/88x31.png)
