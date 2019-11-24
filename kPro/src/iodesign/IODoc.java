package iodesign;

public class IODoc {

/**
 * Dubbo缺省协议采用单一长连接和NIO异步通讯
 */

/**
 * https://www.jianshu.com/p/bae386af45ca
 * 按照posix标准，系统io分为同步io和异步io两种，其中同步io常用的是bio nio。异步io有aio。
 * 从程序的角度来看，bio在读和写的时候，会阻塞，只有当程序将流写入操作系统或者读到流后，阻塞才会结束，线程接着run下去。
 * 而nio和aio属于非阻塞方式，他们都是基于事件驱动思想，但是nio采用的是reactor 模式，而aio采用的是proactor模式。
 *
 * Reactor 模式使用event loop 阻塞等在io上，一但io可以读或写，通过分发器，遍历事件注册队列，将事件分发到指定注册的处理器。
 * 由应用的处理器来再将流读取到缓冲区或写入操作系统，完成io操作。
 *
 * Proctor 模式下读和写的方法是异步的，只需调用读和写即可。当有流可读取的时候，操作系统会将流传入read方法缓存区，并通知应用程序。
 * 对于写，当操作系统将writer 写入完毕时，操作系统会主动通知应用程序。
 * proactor模式的Aio，流的读取和写入由操作系统完成，省去了遍历事件通知队列selector 的代价。
 * Windows上的iocp实现了aio，linux目前只有基于epoll模拟实现的aio。
 *
 */


/**
 * select/poll: 水平触发   Reactor
 * epoll： 水平触发，边缘触发     Reactor/Proactor    在Linux系统中并没有Windows中的IOCP技术，所以Linux技术使用epoll多路复用技术模拟异步IO
 * kqueue：  Proactor
 * Level_triggered(水平触发)：当被监控的文件描述符上有可读写事件发生时，epoll_wait()会通知处理程序去读写。
 * 如果这次没有把数据一次性全部读写完(如读写缓冲区太小)，那么下次调用 epoll_wait()时，
 * 它还会通知你在上没读写完的文件描述符上继续读写，当然如果你一直不去读写，它会一直通知你！！！
 * 如果系统中有大量你不需要读写的就绪文件描述符，而它们每次都会返回，这样会大大降低处理程序检索自己关心的就绪文件描述符的效率！！！
 *
 * Edge_triggered(边缘触发)：当被监控的文件描述符上有可读写事件发生时，epoll_wait()会通知处理程序去读写。
 * 如果这次没有把数据全部读写完(如读写缓冲区太小)，那么下次调用epoll_wait()时，它不会通知你，也就是它只会通知你一次，
 * 直到该文件描述符上出现第二次可读写事件才会通知你！！！这种模式比水平触发效率高，系统不会充斥大量你不关心的就绪文件描述符！！！
 */


/**
 * 在Netty编程中，将select 切换成 epoll十分方便。
 *
 * 代码上需要修改两个地方： NioEventLoopGroup 替换成 EpollEventLoopGroup
 * NioServerSocketChannel 替换成 EpollServerSocketChannel
 *
 *
 * Oracle JDK在Linux已经默认使用epoll方式， 为什么netty还要提供一个基于epoll的实现呢?
 * Netty的 epoll transport使用 epoll edge-triggered 而 java的 nio 使用 level-triggered
 * 另外Netty epoll transport 暴露了更多的nio没有的配置参数， 如 TCP_CORK, SO_REUSEADDR
 */


/**
 *   同步和异步是针对应用程序和内核的交互而言的，同步指的是用户进程触发IO操作并等待或者轮询的去查看IO操作是否就绪，
 * 而异步是指用户进程触发IO操作以后便开始做自己的事情，而当IO操作已经完成的时候会得到IO完成的通知（异步的特点就是通知）。
 *   阻塞和非阻塞是针对于进程在访问数据的时候，根据IO操作的就绪状态来采取的不同方式，说白了是一种读取或者写入操作函数的实现方式，
 * 阻塞方式下读取或者写入函数将一直等待，而非阻塞方式下，读取或者写入函数会立即返回一个状态值。
 */


/**
 * https://blog.csdn.net/u010963948/article/details/78507255
 * 多路复用IO技术 是需要操作系统进行支持的，其特点就是操作系统可以同时扫描同一个端口上不同网络连接的时间。
 * 所以作为上层的JVM，必须要为 不同操作系统的多路复用IO实现 编写不同的代码
 *
 * 例如linux操作系统下的DefaultSelectorProvider的实现，  kernels 2.4内核版本之前，默认使用select
 * 如果内核版本>=2.6则，具体的SelectorProvider为EPollSelectorProvider，否则为默认的PollSelectorProvider
 *
 * Java NIO根据操作系统不同， 针对nio中的Selector有不同的实现：
 * macosx:KQueueSelectorProvider
 * solaris:DevPollSelectorProvider
 * Linux:EPollSelectorProvider (Linux kernels >= 2.6)或PollSelectorProvider
 * windows:WindowsSelectorProvider
 */

}
