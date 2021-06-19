import java.util.ArrayList;
import java.util.LinkedList;


public class graphs {

    public static class Edge{
        int v = 0;
        int w = 0;

        public Edge(int v,int w){
            this.v = v;
            this.w = w;
        }
    }

    static int N = 7;
    
    @SuppressWarnings("unchecked")
    static ArrayList<Edge>[] graph = new ArrayList[N];

    public static void addEdge(int u,int v,int w){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }




    public static void display(){
        for(int i=0;i<N;i++){
            System.out.print(i + " -> ");
            for(Edge e: graph[i]){
                System.out.print("(" + e.v + ", " + e.w + "), ");
            }

            System.out.println();
        }
    }

    public static int searchVtx(int u,int v){
        for(int i=0;i<graph[u].size();i++){
            Edge e = graph[u].get(i);
            if(e.v == v) return i;
        }

        return -1;
    }

    public static void removeEdge(int u,int v){
        int l1 = searchVtx(u,v);
        graph[u].remove(l1);

        int l2 = searchVtx(v,u);
        graph[v].remove(l2);
    }

    public static void removeVtx(int u){
        for(int i = graph[u].size()-1;i>=0;i--){
            Edge e = graph[u].get(i);
            removeEdge(u,e.v);
        }
    }

    public static boolean hasPath(int src,int dest,boolean[] vis){
        if(src == dest) return true;

        vis[src] = true;
        boolean res = false;
        for(Edge e: graph[src]){
            if(!vis[e.v])
               res = res || hasPath(e.v,dest,vis);
        }

        return res;
    }

    public static int allPath(int src,int dest,boolean[] vis,String psf,int wsf){
        if(src == dest){
            System.out.println(psf + src + "@" + wsf);
            return 1;
        }

        vis[src] = true;
        int count = 0;
        for(Edge e: graph[src]){
            if(!vis[e.v])
            count+=allPath(e.v,dest,vis,psf + src + " ", wsf + e.w);
            
        }
        
        vis[src] = false;
        return count;
    }

    public static class pair{
        String path = "";
        int weight = 0;
        boolean isDestiHit = false;

        pair(String path,int weight,boolean isDestiHit){
            this.path = path;
            this.weight = weight;
            this.isDestiHit = isDestiHit;
        }
    }

    public static pair maxWeightPath(int src,int dest,boolean[] vis){
        if(src==dest){
            return new pair(src + "",0,true);
        }

        vis[src] = true;
        
        pair myAns = new pair("",0,false);
        for(Edge e : graph[src]){
            if(!vis[e.v]){
                pair recAns = maxWeightPath(e.v,dest,vis);
                if(recAns.isDestiHit && recAns.weight + e.w > myAns.weight){
                    myAns.weight = recAns.weight + e.w;
                    myAns.path = src + " " + recAns.path;
                    myAns.isDestiHit = true;
                }
            }
        }

        vis[src] = false;
        return myAns;
    }

    public static int hamintonianPath(int src,int osrc,boolean[] vis,int edgeCount,String psf){
        if(edgeCount == N - 1){
            psf += src;
            int idx = searchVtx(src,osrc);
            
            if(idx != -1) System.out.println("Cycle : " + psf);
            else  System.out.println("Path : " + psf);

            return 1;
        }

        vis[src] = true;
        int count = 0;
        for(Edge e: graph[src]){
            if(!vis[e.v])
              count += hamintonianPath(e.v,osrc,vis,edgeCount + 1,psf + src + " "); 
        }

        vis[src] = false;
        return count;
    }

//BFS.=======================================================

public static void BFS_01(int src,boolean[] vis){
    LinkedList<Integer> que = new LinkedList<>();
    que.addLast(src);

    boolean cycle = false;
    while(que.size()!=0){
        int vxt = que.removeFirst();
        
        if(vis[vtx]){
            cycle = true;
            continue;
        }

        vis[vtx] = true;
        
        for(Edge e: graph[vtx]){
            if(!vis[e.v]){
                que.addLast(e.v);
            }
        }
    }
}

public static void BFS_02(int src,boolean[] vis){
    LinkedList<Integer> que = new LinkedList<>();
    que.addLast(src);
    que.addLast(null);

    boolean cycle = false;
    int level = 0;

    while(que.size() != 1){
        int vxt = que.removeFirst();
        
        if(vis[vtx]){
            cycle = true;
            continue;
        }

        if(vtx == dest){
            System.out.println(level);
        }

        vis[vtx] = true;
        
        for(Edge e: graph[vtx]){
            if(!vis[e.v]){
                que.addLast(e.v);
            }
        }

        if(que.getFirst() == null){
            level++;
            que.addLast(que.removeFirst());
        }
    }
}

public static void BFS_03_WithCycle(int src,boolean[] vis){
    LinkedList<Integer> que = new LinkedList<>();
    que.addLast(src);

    boolean cycle = false;
    int level = 0;
    
    int[] dis = new int[N];

    while(que.size() != 0){
        int size = que.size();
        while(size-->0){
            int vxt = que.removeFirst();
        
            if(vis[vtx]){
                cycle = true;
                continue;
            }
            
            dis[vtx] = level;

            vis[vtx] = true;
            for(Edge e: graph[vtx]){
                if(!vis[e.v]){
                    que.addLast(e.v);
                }
            }                
        }
        level++;
    }
}

public static void BFS_04_WithoutCycle(int src,boolean[] vis){
    LinkedList<Integer> que = new LinkedList<>();
    que.addLast(src);
    vis[src] = true;

    int level = 0;
    
    int[] dis = new int[N];

    while(que.size() != 0){
        int size = que.size();
        while(size-->0){
            int vxt = que.removeFirst();
            
            dis[vtx] = level;
            for(Edge e: graph[vtx]){
                if(!vis[e.v]){
                    vis[e.v] = true;
                    que.addLast(e.v);
                }
            }                
        }
        level++;
    }
}


        int N = graph.length;
        int[] markedColor = new int[N];
        Arrays.fill(markedColor, -1);
        
        boolean ans = true;
        for(int i=0;i<N;i++){
            if(markedColor[i] == -1){
                ans = isBipartite(graph,markedColor,i);
                
                if(!ans) return false;
            }
        }
        
        return true;
    }






    

